package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.*;
import vo.ActionForward;

@WebServlet("*.me")
public class MemberFrontController extends HttpServlet {
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		String command = request.getServletPath();
		System.out.println("command : " + command);
		
		Action action = null;
		ActionForward forward = null;
		
		if(command.equals("/MemberManagementForm.me")) {
			forward = new ActionForward();
			forward.setPath("/member/member_management_form.jsp");
			forward.setRedirect(false);
		} else if(command.equals("/MemberManagementAction.me")) {
			action = new MemberManagementAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/MemberManagementProAction.me")) {
			action = new MemberManagementProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MemberLoginForm.me")) {
			forward = new ActionForward();
			forward.setPath("/member/member_login_form.jsp");
			forward.setRedirect(false);
		} else if(command.equals("/MemberJoinPro.me")) {
			action = new MemberJoinProAction();

			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/MemberJoinResult.me")) {
			forward = new ActionForward();
			forward.setPath("/member/member_join_result.jsp");
			forward.setRedirect(false);
		} else if(command.equals("/MemberLoginPro.me")) {
			action = new MemberLoginProAction();

			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/MemberLogoutForm.me")) {
			action = new MemberLogoutAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 1. ActionForward ????????? ???????????? ????????? ??????
		if(forward != null) {
			// 2. ActionForward ?????? ?????? isRedirect ?????? true(= Redirect ??????) ?????? ??????
			if(forward.isRedirect()) { // true = Redirect ??????
				// response ????????? sendRedirect() ???????????? ???????????? Redirect ?????? ?????????
				// => ???????????? : ActionForward ????????? ????????? ??????(path)
				response.sendRedirect(forward.getPath());
			} else { // false = Dispatcher ??????
				// request ????????? getRequestDispatcher() ???????????? ???????????? ????????? ?????? ??????
				// => ???????????? : ActionForward ????????? ????????? ??????(path)
				//    ???????????? : RequestDispatcher
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				// RequestDispatcher ????????? forward() ???????????? ???????????? ????????? ?????? ??????
				// => ???????????? : request, response ??????
				dispatcher.forward(request, response);
			}
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
