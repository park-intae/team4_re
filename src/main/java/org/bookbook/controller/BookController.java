package org.bookbook.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpSession;

import org.bookbook.domain.BookSearchVO;
import org.bookbook.domain.BookVO;
import org.bookbook.domain.GenreVO;
import org.bookbook.domain.LikeVO;
import org.bookbook.domain.TopicVO;
import org.bookbook.model.Criteria;
import org.bookbook.model.PageMakerDTO;
import org.bookbook.service.BookSearchService;
import org.bookbook.util.SidebarUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

	@Autowired
	SidebarUtil sidebarUtil;

	@ModelAttribute("searchBook")
	public JSONObject searchBookTypes(TopicVO topics, GenreVO genres) {
		JSONObject result = sidebarUtil.searchBookTypes(topics, genres);
		return result;
	}

	@GetMapping("/list")
	public void list(@ModelAttribute("search") BookSearchVO search, Model model, Criteria cri,
			@RequestParam("selectedTopics") String[] topic) {

		cri.setTopics(topic);

		String flaskApiUrl = "http://49.50.166.252:5000/api/list";

		String keywordParam = (search.getKeywords() != null) ? String.join(",", search.getKeywords()) : "";

		RestTemplate restTemplate = new RestTemplate();

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(flaskApiUrl)
				.queryParam("keyword", keywordParam);
		String response = restTemplate.getForObject(builder.toUriString(), String.class);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response);

			JsonNode resultNode = jsonNode.get("result");
			
			List<Long> bookIds = new ArrayList<Long>();
			
			Random random = new Random();

			for (int i = 0; i < 5; i++) {
				Long randomNumber = (long) (random.nextInt(11242) + 1);
				bookIds.add(randomNumber);
			}
			
			if (resultNode != null && resultNode.isArray()) {

				List<Long> bookIdsGetServer = StreamSupport.stream(resultNode.spliterator(), false).map(JsonNode::asLong)
						.collect(Collectors.toList());

				if (!bookIdsGetServer.isEmpty()) {
					bookIds = bookIdsGetServer;
				}

			}
			
			List<BookVO> books = service.getBookListById(bookIds);

			model.addAttribute("bookByCBF", books);
			
		} catch (Exception e) {
			System.out.println("------------>Error");
		}

		List<BookVO> dataResult = service.getListPaging(cri);

		model.addAttribute("list", dataResult);

		int total = service.getTotal(keywordParam);
		System.out.println("keywordParam1:"+keywordParam);
		System.out.println("total:"+total);
		
		PageMakerDTO pagemake = new PageMakerDTO(cri, total);

		model.addAttribute("pageMaker", pagemake); // 키 : 밸류

	}

	@GetMapping("/detail")
	public void detail(@RequestParam("bookid") int bookid, Model model, HttpSession session) {

		SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");

		String username = "";

		if (securityContext != null && securityContext.getAuthentication() != null) {
			Object principal = securityContext.getAuthentication().getPrincipal();

			if (principal instanceof UserDetails) {
				username = ((UserDetails) principal).getUsername();

				model.addAttribute("username", username);

			}
		}

		BookVO book = service.getBookById(bookid);

		String flaskApiUrlIBCF = "http://49.50.166.252:5000/api/ibcf";

		RestTemplate restTemplate = new RestTemplate();

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(flaskApiUrlIBCF).queryParam("bookid", bookid);
		String response = restTemplate.getForObject(builder.toUriString(), String.class);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response);

			JsonNode resultNode = jsonNode.get("result");
			
			List<Long> bookIds = new ArrayList<Long>();
			
			Random random = new Random();

			for (int i = 0; i < 5; i++) {
				Long randomNumber = (long) (random.nextInt(11242) + 1);
				bookIds.add(randomNumber);
			}
			
			if (resultNode != null && resultNode.isArray()) {

				List<Long> bookIdsGetServer = StreamSupport.stream(resultNode.spliterator(), false).map(JsonNode::asLong)
						.collect(Collectors.toList());

				if (!bookIdsGetServer.isEmpty()) {
					bookIds = bookIdsGetServer;
				}

			}
			
			List<BookVO> books = service.getBookListById(bookIds);

			model.addAttribute("bookByCBF", books);
			
		} catch (Exception e) {
			System.out.println("------------>Error");
		}


		model.addAttribute("book", book);
	}

	@GetMapping("/likes")
	public String getLikes(Model model, @RequestParam(required = false) String userId) { // ?userId="test2"
		if (userId == null) {
		}
		List<LikeVO> likes = service.getLikes(userId);
		log.info(likes);

		model.addAttribute("likes", likes);
		return "book/likes";
	}

	@GetMapping(value = "/detail/{bookId}/title", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String getBookTitle(@PathVariable("bookId") int bookId) {
		try {
			BookVO book = service.getBookById(bookId);
			return book.getTitle(); // 책 제목을 반환
		} catch (Exception e) {
			log.error("Book title fetching error", e);
			return "Unknown Title";
		}
	}

}
