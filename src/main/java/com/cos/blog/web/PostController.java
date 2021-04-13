package com.cos.blog.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.blog.config.auth.PrincipalDetails;
import com.cos.blog.domain.post.Post;
import com.cos.blog.domain.post.PostRepository;
import com.cos.blog.service.PostService;
import com.cos.blog.utils.Paginator;
import com.cos.blog.web.dto.CMRespDto;
import com.cos.blog.web.post.dto.PostSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PostController {

	private final PostService postService;
	
	private static final Integer POSTS_PER_PAGE = 4;
    private static final Integer PAGES_PER_BLOCK = 3;
		
	
	@GetMapping("/")
	public String findAll(Model model,  @RequestParam(value = "page", defaultValue = "1") Integer page,
			@AuthenticationPrincipal PrincipalDetails details) {
		
		
		System.out.println("받은 페이지값: " + page);

	       try {
	            Paginator paginator = new Paginator(PAGES_PER_BLOCK, POSTS_PER_PAGE, postService.글개수());
	            Map<String, Object> pageInfo = paginator.getFixedBlock(page);

	            model.addAttribute("pageInfo", pageInfo);
	        } catch(IllegalStateException e) {
	            model.addAttribute("pageInfo", null);
	            System.err.println(e);
	        }
	       
	       
		model.addAttribute("posts",postService.findAllByOrderByIdDesc(page, POSTS_PER_PAGE));
		return "post/list"; 
	}
	
	@GetMapping("/post/saveForm")
	public String saveForm() {
		
		return "post/saveForm";
	}
	
	@GetMapping("/post/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		Post postEntity = postService.상세보기(id);
		model.addAttribute("post",postEntity);
		return "post/updateForm";
	}
	
	@GetMapping("/post/{id}")
	public String detail(@PathVariable int id, Model model) {
		Post postEntity = postService.상세보기(id);
		model.addAttribute("post", postEntity);
		
		return "post/detail"; //ViewResolver 
	}
	
	@PostMapping("/post")
	public String save(@Valid PostSaveReqDto postSaveReqDto, BindingResult bindingResult, 
			@AuthenticationPrincipal PrincipalDetails details) {
		
		Post post = postSaveReqDto.toEntity();
		post.setUser(details.getUser());
		Post postEntity = postService.글쓰기(post);
		
		System.out.println("postEntity: " + postEntity);
		
		if(postEntity == null) { //이미 AOP 에서 처리했지만 2중으로.
			return "post/saveForm";
			
		}else {
			return "redirect:/";  //AOP 가 문제네, AOP 처리하면 redirect를 안 먹는데?????
		}
		
	}
	
	
	@GetMapping("/post/search")
	public String search(Model model, String keyword,
			@PageableDefault(sort = "id",direction = Sort.Direction.DESC, size = 4) Pageable pageable) {
		
		System.out.println("키워드 "+keyword);
		System.out.println("offset: "+ pageable.getOffset());
		Page<Post> posts = postService.검색하기(keyword,pageable);	
		
		System.out.println(posts);
		System.out.println(posts.getTotalElements());
		System.out.println(posts.isLast());
		System.out.println(posts.isFirst());
			
		model.addAttribute("posts",posts);
		model.addAttribute("keyword",keyword);
		
		return "post/searchlist";
	}
	
	@PutMapping("/post/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable int id, @Valid @RequestBody PostSaveReqDto postSaveReqDto,  
			BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails ){
		//세션 인증이 아니라 저장이므로, 서비스에게 맡겨도 전혀 관점지향프로그래밍에 어긋나지 않는다. 
		int result = postService.수정하기(id, postSaveReqDto, principalDetails.getUser().getId());
		return new CMRespDto<>(result, null);
	}
	
	
	
	@DeleteMapping("/post/{id}")
	public @ResponseBody CMRespDto<?> deleteById(@PathVariable int id,  @AuthenticationPrincipal PrincipalDetails principalDetails){
		int result = postService.삭제하기(id, principalDetails.getUser().getId());
		return new CMRespDto<>(result,null);
	}
	
	
	
}
