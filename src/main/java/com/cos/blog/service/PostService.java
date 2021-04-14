package com.cos.blog.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.domain.post.Post;
import com.cos.blog.domain.post.PostRepository;
import com.cos.blog.web.post.dto.PostSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
	
	@Transactional(readOnly = true) //1.변경감지 안하도록 하고(쓸데없는 연산제거), 2.고립성 유지 
	public Page<Post> 전체찾기(Pageable pageable){
		return postRepository.findAll(pageable);
	}
	
	
	@Transactional
	public Post 글쓰기(Post post) {
		return postRepository.save(post);
	}
	
	@Transactional
	public int 삭제하기(int id, int userId) {
		Post postEntity = postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다.");
		});
				
		if(postEntity.getUser().getId() == userId) {
			postRepository.deleteById(id);
			return 1;
		}else {
			return -1;
		}		
	}
	
	
	@Transactional
	public Post 상세보기(int id){
		
		int result = postRepository.mUpateCount(id);
		
		if(result == 1) {
			return postRepository.findById(id).orElseThrow(()->{
				return new IllegalArgumentException("id를 찾을 수 없습니다.");
			});		
		}else {
			throw new IllegalArgumentException("id를 찾을 수 없습니다.");
		}
	}
	
	
	@Transactional
	public int 수정하기(int id, PostSaveReqDto postSaveReqDto, int userId) {
		Post postEntity = postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다.");
		});
				
		
		if(postEntity.getUser().getId() == userId) {
			postEntity.setTitle(postSaveReqDto.getTitle());
			postEntity.setContent(postSaveReqDto.getContent());
			return 1;
		}else {
			return -1;
		}

	}//더티체킹
	
	
	@Transactional(readOnly = true) //1.변경감지 안하도록 하고(쓸데없는 연산제거), 2.고립성 유지 
	public Page<Post> 검색하기(String keyword, Pageable pageable){		
		return postRepository.findByKeyword(keyword,pageable);
	}
	
	
	@Transactional(readOnly = true)  
	public Long 글개수(){		
		return postRepository.count();
	}
	
    // 페이지로 가져오기
    @Transactional(readOnly = true)
    public Page<Post> findAllByOrderByIdDesc(Integer pageNum, Integer postsPerPage) {
        Page<Post> page = postRepository.findAll(
                // PageRequest의 page는 0부터 시작
                PageRequest.of(pageNum - 1, postsPerPage,
                        Sort.by(Sort.Direction.DESC, "id")
        ));
        
        return page;
        
        //return page.stream().map(Post::new).collect(Collectors.toList());
        

    }

	
}
