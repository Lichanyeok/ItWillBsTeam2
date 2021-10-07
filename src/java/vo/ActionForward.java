package vo;

public class ActionForward {
	// 포워딩을 수행하기 위해 필요한 정보(포워딩 경로(= URL)와 포워딩 방식)를 저장하는 용도
	// = 자바빈 객체 역할 수행
	private String path; // 포워딩 경로(URL)를 저장할 멤버변수
	private boolean isRedirect; // 포워딩 방식을 저장할 멤버변수
	// => true : Redirect 방식, false : Dispatcher 방식
	
	// 멤버변수에 대한 접근을 위해 Getter/Setter 정의
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
}
