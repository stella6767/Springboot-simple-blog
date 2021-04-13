package com.cos.blog.web.post.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cos.blog.domain.post.Post;

import lombok.Data;

@Data
public class PostSaveReqDto {
	
	@NotNull
	@NotBlank(message = "제목을 입력하지 않았습니다.")
	private String title;
	
	@NotNull
	@NotBlank(message = "내용을 입력하지 않았습니다.")
	private String content;
	
	public Post toEntity() {
		return Post.builder()
				.title(title)
				.content(content)
				.build();
	}
}
