<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.profileimage {
	border-radius: 50%;
	cursor: pointer;
	position: relative;
	width: 100px;
	height: 100px;
	vertical-align: middle;
}

</style>

<table border="1">
	<c:forEach var="avarlist" items="${alist}">
		<tr>
			<td style="cursor:pointer"><c:if test="${avarlist.m_profileimage eq 'defaultprofile.png'}">
						<img src="/TIMS/IMG/user/defaultprofile.png" class="profileimage" alt=""></c:if>
				<c:if test="${avarlist.m_profileimage ne 'defaultprofile.png'}">
						<img src="/TIMS/IMG/user/${avarlist.m_id}/profile_IMG/${avarlist.m_profileimage}" class="profileimage" alt=""></c:if>
						<span onclick="window.location='/TIMS/user.com?m_id=${avarlist.m_id}'">${avarlist.m_id} (${avarlist.m_name})</span></td>
		</tr>
	</c:forEach>	
</table>
