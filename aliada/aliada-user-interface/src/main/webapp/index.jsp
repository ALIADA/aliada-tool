<%
response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
String newLocn = new String(request.getContextPath() + "/logon.action;jsessionid=" + request.getSession().getId());
response.setHeader("Location",newLocn);
%>