<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul class="nav default-sidenav">
    <c:forEach items="${leftMenuList }" var="leftMenuListItem">
        <c:if test="${leftMenuListItem.visiable ||leftMenuListItem.menuId eq leftMenuId}">
            <li <c:if test="${leftMenuListItem.menuId eq leftMenuId}">class="active"</c:if>>
                <a href="${leftMenuListItem.menuHref }" title="${leftMenuListItem.menuName }"><i
                        class="fa ${leftMenuListItem.icon }">&nbsp</i><span
                        class="left-menu-item-name"> ${leftMenuListItem.menuName }</span></a>
            </li>
        </c:if>
    </c:forEach>
</ul>