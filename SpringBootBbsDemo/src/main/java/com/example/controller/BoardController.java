package com.example.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.service.BoardService;
import com.example.vo.BoardVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardController {
	@Autowired
	private BoardService boardService;

	@GetMapping("/list")
	public String list(Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		this.boardService.readAll(map);
		List<BoardVO> list = (List<BoardVO>)map.get("results");
		//log.warn("size = " + list.size());
		list.forEach(board -> {
			String filename = board.getFilename();
			if(filename != null) {
				int lastindex = filename.lastIndexOf(".");
				String ext = filename.substring(lastindex + 1);
				board.setFilename(ext);
			}
		});
		model.addAttribute("boardlist", list);
		return "list";     //  /templates/list.html
	}
	
	@GetMapping("/write")
	public String write() {
		return "write";     // /templates/write.html
	}
	
	@PostMapping("/write")
	public String write(BoardVO boardVo, 
			@RequestParam("company") String company, 
			@RequestParam("file") MultipartFile file) throws Exception {
		String email = boardVo.getEmail();
		if(!email.equals("")) email += "@" + company;
		boardVo.setEmail(email);
		//log.warn("file = " + file.getOriginalFilename());
		//log.warn("email = " + boardVo.getEmail());
		this.boardService.create(boardVo, file);
		return "redirect:/list";
	}
	
	@GetMapping("/view/{idx}")
	public String view(@PathVariable int idx, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idx", idx);
		this.boardService.read(map);
		List<BoardVO> list = (List<BoardVO>)map.get("result");
		BoardVO boardVo = list.get(0);
		
		String name = boardVo.getName();
		name = rechange(name);
		boardVo.setName(name);
		
		String title = boardVo.getTitle();
		title = rechange(title);
		boardVo.setTitle(title);
		
		String contents = boardVo.getContents();
		contents = rechange(contents);
		boardVo.setContents(contents);
		
		String filename = boardVo.getFilename();
		if(filename != null) {
			int lastIndex = filename.lastIndexOf(".");
			String ext = filename.substring(lastIndex + 1);
			model.addAttribute("ext", ext);
			
			lastIndex = filename.lastIndexOf("\\");
			String realFilename = filename.substring(lastIndex + 1);
			model.addAttribute("realFilename", realFilename);
		}
		
		model.addAttribute("board", boardVo);
		return "view";     //  /templates/view.html
	}
	
	private String rechange(String oldStr) {
		String newStr = oldStr.replace("''", "'");   //홑홑따옴표를 홑따옴표로
		newStr = newStr.replace("&lt;", "<");
		newStr = newStr.replace("&gt;", ">");
		return newStr;
	}
	
	@GetMapping(value = "/download")
	public void download(@RequestParam("filename") String filename, 
			HttpServletResponse response) {
		File file = new File(filename);
		int lastIndex = filename.lastIndexOf("\\");
		String filename2 = filename.substring(lastIndex + 1);
		try {
			filename2 = new String(filename2.getBytes("utf-8"), "ISO-8859-1");
		}catch(UnsupportedEncodingException ex) {
			log.error(ex.getMessage());
		}
		response.setHeader("Content-Type", "application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename2 + ";");
		//log.warn("경로 = " + file.getAbsolutePath());
		//log.warn("파일 존재 여부 = " + file.exists());
		//log.warn("파일명 = " + filename2);
		try(InputStream is = new FileInputStream(file)){
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		}catch(Exception ex) {
			log.error("여기");
		}
	}
}
