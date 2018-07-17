package tims.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;

@Aspect
public class Advice{
	
	/*@Before("execution(public * test.spring.bean..*(..))")
	public void test(JoinPoint jp ) {
		System.out.println("joinPoint=Before=>>"+jp.getTarget()); // getAction�⑨옙 揶쏆늿占쏙옙肉�占쎈막
	}
	@After("execution(public * test.spring.bean..*(..))")
	public void after(JoinPoint jp ) {
		System.out.println("joinPoint=After=>>"+jp.getTarget()); // getAction�⑨옙 揶쏆늿占쏙옙肉�占쎈막
	}*/ // tims.bean.chatRoom 占쎈솭占쎄텕筌욑옙占쎌뵠�뵳占� ..(..
	@Around("execution(public * tims.bean..main*(..))")
	public Object around(ProceedingJoinPoint jp) throws Throwable { // around占쎈퓠筌랃옙 ProceedingJoinPoint�몴占� 占쎈쑅占쎈튊占쎈맙(proceed()�몴占� 占쎈쑅占쎈튊占쎈릭疫뀐옙 占쎈뻘�눧紐꾨퓠)
		HttpServletRequest request = null;
		Object [] obj = jp.getArgs(); // getArgs : aop占쎈퓠  椰꾨챶�봺占쎈뮉 筌뤴뫀諭� 揶쏆빘猿�
		for(Object o:obj) { // 揶쏆빘猿� 占쎌읈筌ｏ옙 占쎈뼄 獄쏆꼶�궗
			if(o instanceof HttpServletRequest) { 
				request = (HttpServletRequest)o;
			}
		}
		if(request !=null) {
			HttpSession session = request.getSession();
			String id = (String)session.getAttribute("memId");
			if(id == null || "".equals(id)) { //  id揶쏉옙 占쎄섯占쎌뵠椰꾧퀡援� �뜮�뜃�궗獄쏄퉮�뵬占쎈르
				// 獄쏉옙 占쎄돌揶쏉옙疫뀐옙 占쎈꺖占쎈뮞 占쎈툡占쎌뒄 (chatRoomExit)
				return "/member/mainLogin"; // 筌롫뗄�뵥嚥≪뮄�젃占쎌뵥占쎌몵嚥∽옙 癰귣�沅→묾占�
			}
		}
		return jp.proceed(); // 域밸챶占썸에占� 筌욊쑵六억옙釉�疫뀐옙
	}	
	/*@AfterReturning("execution(public * test.spring.bean..*(..))")
	public void AfterReturning(JoinPoint jp) {
		System.out.println("AfterReturning==>"+jp.getTarget());
	}
	
	@AfterThrowing("execution(public * test.spring.bean..*(..))")
	public void AfterThrowing(JoinPoint jp) {
		System.out.println("AfterThrowing==>>"+jp.getTarget());
	}*/
}
