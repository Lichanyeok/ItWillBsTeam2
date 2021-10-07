package action;

import vo.ActionForward;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * XXXAction 클래스의 작업을 수행할 메서드 형식을 강제(통일)하기 위해
 * 부모에 해당하는 Action 인터페이스를 정의하고
 * XXXAction 클래스에서 상속받아 구현할 메서드를 추상메서드 형태로 제공
 * => 메서드명 : execute
 * => 접근제한 : 전체 공개
 * => 파라미터 : HttpServletRequest 객체, HttpServletResponse 객체
 * => 리턴타입 : ActionForward 타입
 * => 예외 발생 시 모든 예외를 외부로 위임(던지기)
 */
public interface Action {
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception;
}
















