package org.bookbook.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.bookbook.domain.GenreVO;
import org.bookbook.domain.TopicVO;
import org.bookbook.service.BookSearchService;
import org.bookbook.util.SidebarUtil;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	BookSearchService service;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	SidebarUtil sidebarUtil;	
	
	@ModelAttribute("searchBook")
	public JSONObject searchBookTypes(TopicVO topics, GenreVO genres) {
		JSONObject result = sidebarUtil.searchBookTypes(topics, genres);
		return result;
	}

//	@ModelAttribute("searchBook")
//	public JSONObject searchBookTypes(TopicVO topics, GenreVO genres) {
//		System.out.println("값 들어오냐");
//		System.out.println("topic:"+topics);
//		System.out.println("genres:"+genres);
//		
//		List<TopicVO> topicList = service.getTopicList(topics);
//
//		List<GenreVO> genreList = service.getGenreList(genres);
//
//		Map<String, List<String>> genreConvertedMap = convertToMap(genreList);
//
//		// log.info(genreConvertedMap);
//
//		Map<String, Map<String, List<String>>> map = new LinkedHashMap<>();
//
//		// log.info(topicList);
//
//		for (TopicVO topic : topicList) {
//			Map<String, List<String>> genreMap = new LinkedHashMap<>();
//
//			String genreToString = topic.getGenres();
//
//			// 초기 용량 설정을 통한 성능 최적화
//			List<String> genreToList = new ArrayList<>(Arrays.asList(genreToString.split(", ")));
//
//			for (String genre : genreToList) {
//				// 불필요한 객체 생성 최적화
//				// genre = genre.trim();
//				List<String> categoriesToList = genreConvertedMap.get(genre);
//
//
//				// 이미 있는 리스트를 재활용하여 새로운 리스트를 생성하지 않도록 최적화
//				if (categoriesToList == null) {
//					categoriesToList = new ArrayList<>();
//				}
//
//				genreMap.put(genre, categoriesToList);
//			}
//
//			map.put(topic.getTopic(), genreMap);
//		}
//
//		JSONObject jsonObject = new JSONObject(map);
//
//		return jsonObject;
//	}
//
//	public static Map<String, List<String>> convertToMap(List<GenreVO> genreList) {
//		Map<String, List<String>> genreMap = new HashMap<>();
//
//		for (GenreVO genreVO : genreList) {
//			String genre = genreVO.getGenre();
//			String categoriesToString = genreVO.getCategories();
//
//			List<String> categoriesList = new ArrayList<>();
//
//			if (categoriesToString != null) {
//				categoriesList = new ArrayList<String>(Arrays.asList(categoriesToString.split(", ")));
//			}
//
//			// log.info("------->>>"+categoriesList);
//
//			genreMap.put(genre, categoriesList);
//		}
//
//		// log.info("--------------------->>>>>"+ genreMap);
//
//		return genreMap;
//	}

	
	
	
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		 // 스프링 시큐리티 컨텍스트에서 인증 정보를 가져옵니다.
	     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
	            Object principal = auth.getPrincipal();
	            String username;
	            if (principal instanceof UserDetails) {
	                username = ((UserDetails) principal).getUsername();
	            } else {
	                username = principal.toString(); // principal이 UserDetails 인스턴스가 아닌 경우
	            }
	            model.addAttribute("username", username);
	        }

		
		 // 세션에서 네이버 사용자 정보를 가져와 모델에 추가
        String userId = (String) session.getAttribute("userId");
        String userNickname = (String) session.getAttribute("userNickname");
        String userName = (String) session.getAttribute("userName");
        String userEmail = (String) session.getAttribute("userEmail");
        String userGender = (String) session.getAttribute("userGender");
        String userBirthday = (String) session.getAttribute("userBirthday");
	    
        // 모델에 사용자 정보 추가
        model.addAttribute("userId", userId);
        model.addAttribute("userNickname", userNickname);
        model.addAttribute("userName", userName);
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("userGender", userGender);
        model.addAttribute("userBirthday", userBirthday);

	  
		return "main";
	}

}
