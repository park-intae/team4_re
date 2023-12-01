package org.bookbook.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.bookbook.domain.GenreVO;
import org.bookbook.domain.TopicVO;
import org.bookbook.domain.UserVO;
import org.bookbook.security.domain.CustomUser;
import org.bookbook.service.BookSearchService;
import org.bookbook.util.SidebarUtil;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
//		System.out.println("媛� �뱾�뼱�삤�깘");
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
//			// 珥덇린 �슜�웾 �꽕�젙�쓣 �넻�븳 �꽦�뒫 理쒖쟻�솕
//			List<String> genreToList = new ArrayList<>(Arrays.asList(genreToString.split(", ")));
//
//			for (String genre : genreToList) {
//				// 遺덊븘�슂�븳 媛앹껜 �깮�꽦 理쒖쟻�솕
//				// genre = genre.trim();
//				List<String> categoriesToList = genreConvertedMap.get(genre);
//
//
//				// �씠誘� �엳�뒗 由ъ뒪�듃瑜� �옱�솢�슜�븯�뿬 �깉濡쒖슫 由ъ뒪�듃瑜� �깮�꽦�븯吏� �븡�룄濡� 理쒖쟻�솕
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


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpSession session) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);

		// �뒪�봽留� �떆�걧由ы떚 而⑦뀓�뒪�듃�뿉�꽌 �씤利� �젙蹂대�� 媛��졇�샃�땲�떎.
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {

			if (auth.getPrincipal() instanceof CustomUser) {
				CustomUser customUser = (CustomUser) auth.getPrincipal();
				UserVO user = customUser.getUser();
				 model.addAttribute("username", user.getUsername()); // �궗�슜�옄 �씠由� �삉�뒗 �븘�씠�뵒瑜� 紐⑤뜽�뿉 異붽�
				session.setAttribute("user", user); // UserVO 媛앹껜 �꽭�뀡�뿉 ���옣
			}
		}

		// �꽭�뀡�뿉�꽌 �꽕�씠踰� �궗�슜�옄 �젙蹂대�� 媛��졇�� 紐⑤뜽�뿉 異붽�
		String userId = (String) session.getAttribute("userId");
		String userName = (String) session.getAttribute("userName");
		String userNickname = (String) session.getAttribute("userNickname");
		String userEmail = (String) session.getAttribute("userEmail");
		String userGender = (String) session.getAttribute("userGender");
		String userBirthday = (String) session.getAttribute("userBirthday");

		// 紐⑤뜽�뿉 �궗�슜�옄 �젙蹂� 異붽�
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		model.addAttribute("userNickname", userNickname);
		model.addAttribute("userEmail", userEmail);
		model.addAttribute("userGender", userGender);
		model.addAttribute("userBirthday", userBirthday);

		return "main";
	}

}
