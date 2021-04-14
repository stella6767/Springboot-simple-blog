package com.cos.blog.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.domain.post.Post;
import com.cos.blog.domain.post.PostRepository;
import com.cos.blog.domain.reply.Reply;
import com.cos.blog.domain.reply.ReplyRepository;
import com.cos.blog.domain.user.RoleType;
import com.cos.blog.domain.user.User;
import com.cos.blog.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminService {

	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;

	@Transactional(readOnly = true)
	public Page<User> 유저목록(Integer pageNum, Integer postsPerPage) {

		Page<User> page = userRepository.findAll(
				// PageRequest의 page는 0부터 시작
				PageRequest.of(pageNum - 1, postsPerPage, Sort.by(Sort.Direction.DESC, "id")));

		return page;

	}

	@Transactional(readOnly = true)
	public List<User> 유저검색(String email) {
		return userRepository.mFindByEmail(email);
	}

	@Transactional
	public void 유저권한변경(RoleType role, int id) {
		User userEntity = userRepository.findById(id).get();
		userEntity.setRole(role);
	}

	@Transactional
	public void 유저삭제(int id) {
		userRepository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Long 글개수() {
		return postRepository.count();
	}

	@Transactional(readOnly = true)
	public Long 유저개수() {
		return userRepository.count();
	}

	@Transactional(readOnly = true)
	public Long 댓글개수() {
		return replyRepository.count();
	}

	@Transactional(readOnly = true)
	public Page<Post> 게시글목록(Integer pageNum, Integer postsPerPage) {
		Page<Post> page = postRepository.findAll(
				// PageRequest의 page는 0부터 시작
				PageRequest.of(pageNum - 1, postsPerPage, Sort.by(Sort.Direction.DESC, "id")));

		return page;
	}

	@Transactional(readOnly = true)
	public List<Post> 게시글검색(String keyword) {
		return postRepository.findByKeyword(keyword);
	}

	@Transactional
	public void 게시글삭제(int id) {
		postRepository.deleteById(id);
	}


	

	
	@Transactional(readOnly = true)
	public Page<Reply> 댓글목록(Integer pageNum, Integer postsPerPage) {
		Page<Reply> page = replyRepository.findAll(
				// PageRequest의 page는 0부터 시작
				PageRequest.of(pageNum - 1, postsPerPage, Sort.by(Sort.Direction.DESC, "id")));

		return page;
	}
	

	@Transactional(readOnly = true)
	public List<Reply> 댓글검색(String content) {
		return replyRepository.findByContent(content);
	}
	
	@Transactional
	public void 댓글삭제(int id) {
		replyRepository.deleteById(id);
	}

}
