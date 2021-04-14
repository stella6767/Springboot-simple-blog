package com.cos.blog.web;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blog.domain.post.Post;
import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.user.RoleType;
import com.cos.blog.domain.user.User;
import com.cos.blog.service.AdminService;
import com.cos.blog.utils.Paginator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminController {

	private final AdminService adminService;

	private static final Integer POSTS_PER_PAGE = 4;
	private static final Integer PAGES_PER_BLOCK = 3;

	@GetMapping("/admin/user")
	public String user(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page) {

		try {
			Paginator paginator = new Paginator(PAGES_PER_BLOCK, POSTS_PER_PAGE, adminService.유저개수());
			Map<String, Object> pageInfo = paginator.getFixedBlock(page);

			model.addAttribute("pageInfo", pageInfo);
		} catch (IllegalStateException e) {
			model.addAttribute("pageInfo", null);
			System.err.println(e);
		}

		model.addAttribute("users", adminService.유저목록(page, POSTS_PER_PAGE));

		return "admin/userlist";
	}

	// 유저 검색기능
	@PostMapping("/admin/user")
	public @ResponseBody List<User> searchUser(@RequestBody Map<String, String> data) {

		return adminService.유저검색(data.get("email"));
	}

	// 유저 권한변경
	@PutMapping("/admin/user/{id}")
	public @ResponseBody String updateRole(@RequestBody Map<String, String> roles, @PathVariable int id) {

		String role = roles.get("role");
		System.out.println(role);
		RoleType roleType;

		if (role.equals("ROLE_ADMIN")) {
			roleType = RoleType.ADMIN;
		} else {
			roleType = RoleType.USER;
		}

		adminService.유저권한변경(roleType, id);
		return "성공";
	}

	// 유저 삭제
	@DeleteMapping("/admin/user/{id}")
	public @ResponseBody String deleteUser(@PathVariable int id) {
		adminService.유저삭제(id);
		return "성공";
	}

	@GetMapping("/admin/post")
	public String post(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page) {

		try {
			Paginator paginator = new Paginator(PAGES_PER_BLOCK, POSTS_PER_PAGE, adminService.글개수());
			Map<String, Object> pageInfo = paginator.getFixedBlock(page);

			model.addAttribute("pageInfo", pageInfo);
		} catch (IllegalStateException e) {
			model.addAttribute("pageInfo", null);
			System.err.println(e);
		}

		model.addAttribute("posts", adminService.게시글목록(page, POSTS_PER_PAGE));
		return "admin/postlist";
	}

	// 게시글 검색기능
	@PostMapping("/admin/post")
	public @ResponseBody List<Post> searchPost(@RequestBody Map<String, String> data) {

		String keyword = data.get("keyword");

		return adminService.게시글검색(keyword);
	}

	// 게시글 삭제
	@DeleteMapping("/admin/post/{id}")
	public @ResponseBody String deletePost(@PathVariable int id) {
		adminService.게시글삭제(id);
		return "성공";
	}
	
	
	
	
	

	@GetMapping("/admin/reply")
	public String reply(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page) {

		try {
			Paginator paginator = new Paginator(PAGES_PER_BLOCK, POSTS_PER_PAGE, adminService.댓글개수());
			Map<String, Object> pageInfo = paginator.getFixedBlock(page);

			model.addAttribute("pageInfo", pageInfo);
		} catch (IllegalStateException e) {
			model.addAttribute("pageInfo", null);
			System.err.println(e);
		}
		
		
		model.addAttribute("replys", adminService.댓글목록(page, POSTS_PER_PAGE));

		return "admin/replylist";
	}
	
	
	@PostMapping("/admin/reply")
	public @ResponseBody List<Reply> searchReply(@RequestBody Map<String, String> data) {
		
		String content = data.get("content");
		
		return adminService.댓글검색(content);
	}
	
	@DeleteMapping("/admin/reply/{id}")
	public @ResponseBody String deleteReply(@PathVariable int id ) {
		
		adminService.댓글삭제(id);
		
		return "성공";
	}
	
	
	
	

}
