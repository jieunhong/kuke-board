package kuke.board.comment.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import kuke.board.comment.entity.Comment;
import kuke.board.comment.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @DisplayName("삭제표시만")
    @Test
    void delete() {
        Long articleId = 1L;
        Long commentId = 1L;
        Comment comment = createComment(articleId, commentId);
        given(commentRepository.findById(commentId)).willReturn(java.util.Optional.of(comment));
        given(commentRepository.countBy(articleId, commentId, 2L)).willReturn(2L);

        //when
        commentService.delete(commentId);

        //then
        Mockito.verify(comment).delete();
    }

    @DisplayName("실제 삭제")
    @Test
    void delete_real() {
        Long articleId = 1L;
        Long commentId = 1L;
        Long parentCommentId = 2L;

        Comment comment = createComment(articleId, commentId, parentCommentId);
        given(comment.isRoot()).willReturn(false);
        Comment parentComment = mock(Comment.class);
        given(parentComment.isDeleted()).willReturn(false);

        given(commentRepository.findById(commentId)).willReturn(java.util.Optional.of(comment));
        given(commentRepository.countBy(articleId, commentId, 2L)).willReturn(1L);
        given(commentRepository.findById(parentCommentId)).willReturn(java.util.Optional.of(parentComment));

        //when
        commentService.delete(commentId);

        //then
        verify(commentRepository).delete(comment);
        verify(commentRepository, never()).delete(parentComment);
    }

    @DisplayName("실제 삭제-부모까지")
    @Test
    void delete_real_parent() {
        Long articleId = 1L;
        Long commentId = 1L;
        Long parentCommentId = 2L;

        Comment comment = createComment(articleId, commentId, parentCommentId);
        given(comment.isRoot()).willReturn(false);
        Comment parentComment = createComment(articleId, parentCommentId);
        given(parentComment.isRoot()).willReturn(true);
        given(parentComment.isDeleted()).willReturn(true);

        given(commentRepository.findById(commentId)).willReturn(java.util.Optional.of(comment));
        given(commentRepository.countBy(articleId, commentId, 2L)).willReturn(1L);
        given(commentRepository.findById(parentCommentId)).willReturn(java.util.Optional.of(parentComment));

        //when
        commentService.delete(commentId);

        //then
        verify(commentRepository).delete(comment);
        verify(commentRepository).delete(parentComment);
    }

    private Comment createComment(Long articleId, Long commentId) {

        Comment comment = Mockito.mock(Comment.class);

        Mockito.when(comment.getArticleId()).thenReturn(articleId);
        Mockito.when(comment.getCommentId()).thenReturn(commentId);

        return comment;
    }

    private Comment createComment(Long articleId, Long commentId, Long parentCommentId) {

        Comment comment = Mockito.mock(Comment.class);

        Mockito.when(comment.getArticleId()).thenReturn(articleId);
        Mockito.when(comment.getCommentId()).thenReturn(commentId);
        Mockito.when(comment.getParentCommentId()).thenReturn(parentCommentId);
        return comment;
    }
}