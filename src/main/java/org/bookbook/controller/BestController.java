package org.bookbook.controller;

import org.bookbook.domain.Criteria;
import org.bookbook.domain.GenreVO;
import org.bookbook.domain.PageDTO;
import org.bookbook.domain.TopicVO;
import org.bookbook.service.BestService;
import org.bookbook.util.SidebarUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/best")
public class BestController {
	@Autowired
	private BestService service;

	@Autowired
	SidebarUtil sidebarUtil;

	@ModelAttribute("searchBook")
	public JSONObject searchBookTypes(TopicVO topics, GenreVO genres) {
		JSONObject result = sidebarUtil.searchBookTypes(topics, genres);
		return result;
	}
	
	@GetMapping("/list")
	public void list(@ModelAttribute("cri") Criteria cri, Model model) {
		int total = service.getTotal(cri);
		int total2 = service.getTotal2(cri);
		int total3 = service.getTotal3(cri);
		int total4 = service.getTotal4(cri);
		int total5 = service.getTotal5(cri);
		int total6 = service.getTotal6(cri);

		model.addAttribute("list", service.getList(cri));
		if (cri.getClassi() == 2) {
			model.addAttribute("list", service.getList2(cri));
			model.addAttribute("pageMaker", new PageDTO(cri, total2));
		} else if (cri.getClassi() == 3) {
			model.addAttribute("list", service.getList3(cri));
			model.addAttribute("pageMaker", new PageDTO(cri, total3));
		} else if (cri.getClassi() == 4) {
			model.addAttribute("list", service.getList4(cri));
			model.addAttribute("pageMaker", new PageDTO(cri, total4));
		} else if (cri.getDateNum() == 1) {
			model.addAttribute("list", service.getList5(cri));
			model.addAttribute("pageMaker", new PageDTO(cri, total5));
		} else if (cri.getDateNum2() == 1) {
			model.addAttribute("list", service.getList6(cri));
			model.addAttribute("pageMaker", new PageDTO(cri, total6));
		}
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}

	
	@GetMapping({ "/get" })
	public void get(@RequestParam("column1") int column1, @ModelAttribute("cri") Criteria cri,
			Model model) {
		
		model.addAttribute("best", service.get(column1));
	}
}