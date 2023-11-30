package org.bookbook.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bookbook.domain.BookIdVO;
import org.bookbook.domain.BookSearchVO;
import org.bookbook.domain.BookVO;
import org.bookbook.domain.GenreVO;
import org.bookbook.domain.TopicVO;
import org.bookbook.model.Criteria;
import org.bookbook.model.PageMakerDTO;
import org.bookbook.service.BookSearchService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/book")
@Controller
public class BookController {
	@Autowired
	BookSearchService service;

	@ModelAttribute("searchBook")
	public JSONObject searchBookTypes(TopicVO topics, GenreVO genres) {
		List<TopicVO> topicList = service.getTopicList(topics);

		List<GenreVO> genreList = service.getGenreList(genres);

		Map<String, List<String>> genreConvertedMap = convertToMap(genreList);

		// log.info(genreConvertedMap);

		Map<String, Map<String, List<String>>> map = new LinkedHashMap<>();

		// log.info(topicList);

		for (TopicVO topic : topicList) {
			Map<String, List<String>> genreMap = new LinkedHashMap<>();

			String genreToString = topic.getGenres();

			// 초기 용량 설정을 통한 성능 최적화
			List<String> genreToList = new ArrayList<>(Arrays.asList(genreToString.split(", ")));

			for (String genre : genreToList) {
				// 불필요한 객체 생성 최적화
				// genre = genre.trim();
//				log.info("----->---->" + genre);
				List<String> categoriesToList = genreConvertedMap.get(genre);

//				log.info("----------------->" + categoriesToList);

				// 이미 있는 리스트를 재활용하여 새로운 리스트를 생성하지 않도록 최적화
				if (categoriesToList == null) {
					categoriesToList = new ArrayList<>();
				}

				genreMap.put(genre, categoriesToList);
			}

			map.put(topic.getTopic(), genreMap);
		}

		JSONObject jsonObject = new JSONObject(map);

		return jsonObject;
	}

	public static Map<String, List<String>> convertToMap(List<GenreVO> genreList) {
		Map<String, List<String>> genreMap = new HashMap<>();

		for (GenreVO genreVO : genreList) {
			String genre = genreVO.getGenre();
			String categoriesToString = genreVO.getCategories();

			List<String> categoriesList = new ArrayList<>();

			if (categoriesToString != null) {
				categoriesList = new ArrayList<String>(Arrays.asList(categoriesToString.split(", ")));
			}

			// log.info("------->>>"+categoriesList);

			genreMap.put(genre, categoriesList);
		}

		// log.info("--------------------->>>>>"+ genreMap);

		return genreMap;
	}

	@GetMapping("/list")
	public void list(@ModelAttribute("search") BookSearchVO search, Model model, Criteria cri) {

//		log.info("list Page");
//		log.info(search);
		
        String flaskApiUrl = "http://49.50.166.252:5000/api/list";

        RestTemplate restTemplate = new RestTemplate();

        log.info("Start!!!!");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(flaskApiUrl)
                .queryParam("keyword", search.getKeywords() != null ? String.join(",", search.getKeywords()) : "")
                .queryParam("topic",
                        search.getTopics() != null ? String.join(",", search.getTopics()) : "")
                .queryParam("genre", search.getBookType() != null ? String.join(",", search.getBookType()) : "")
                .queryParam("category",
                        search.getSelectedCategories() != null ? String.join(",", search.getSelectedCategories()) : "");

        log.info(builder);

        log.info("End!!!!");

        String response = restTemplate.getForObject(builder.toUriString(), String.class);

        log.info("Flask API : " + response);
        

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);

            // "result" 키에 해당하는 값을 추출
            JsonNode resultNode = jsonNode.get("result");

            System.out.println(resultNode);
            
            System.out.println("Start!!!");
            
            if (resultNode != null && resultNode.isArray()) {
            	
            	System.out.println("Success!!!1");
                List<Long> bookIds = StreamSupport.stream(resultNode.spliterator(), false)
                        .map(JsonNode::asLong)
                        .collect(Collectors.toList());
                System.out.println("Success!!!2");


                // bookIds를 이용하여 책 정보를 추출하는 로직 추가
                List<BookVO> books = service.getBookListById(bookIds);
                System.out.println("Success!!!3");
                
//                System.out.println("---------->"+books);

        		model.addAttribute("bookByCBF", books);
            }


        } catch (Exception e) {
            System.out.println("------------>Null");
        }
        
		
		List<BookVO> dataResult = service.getListPaging(cri);

		model.addAttribute("list", dataResult);
		
//		log.info("dataResult:"+dataResult);
		
		int total = service.getTotal();
		 
		PageMakerDTO pagemake = new PageMakerDTO(cri, total);

		model.addAttribute("pageMaker", pagemake); // 키 : 밸류
		
		

		// log.info(model);

	}
}
