<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en-US">
<head>
    <jsp:useBean id="controller" class="com.example.oopsem3lab1.Beans.Controller" scope="request"/>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Trip management</title>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/pages/CSS/woocommerce-layout.css' type='text/css'
          media='all'/>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/pages/CSS/woocommerce-smallscreen.css'
          type='text/css' media='only screen and (max-width: 768px)'/>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/pages/CSS/woocommerce.css' type='text/css'
          media='all'/>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/pages/CSS/font-awesome.min.css' type='text/css'
          media='all'/>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/pages/CSS/style.css' type='text/css' media='all'/>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/pages/CSS/easy-responsive-shortcodes.css'
          type='text/css' media='all'/>
    <link rel='stylesheet'
          href='https://fonts.googleapis.com/css?family=Oswald:400,500,700%7CRoboto:400,500,700%7CHerr+Von+Muellerhoff:400,500,700%7CQuattrocento+Sans:400,500,700'
          type='text/css' media='all'/>
</head>
<body class="blog">
<div id="page">
    <div class="container">
        <header id="masthead" class="site-header">
            <div class="site-branding">
                <h1 class="site-title">Around the globe</h1>

                <%
                    if (request.getParameterMap().containsKey("logout")) {
                        controller.logout(request);
                        response.sendRedirect("/index");
                    }
                %>

                <button name="logoutBtn" onclick="location.href = '${pageContext.request.contextPath}/index?logout'">
                    Logout
                </button>
            </div>
            <nav id="site-navigation" class="main-navigation">
                <ul id="menu-menu-1" class="menu">
                    <li><a href="${pageContext.request.contextPath}/index">Home</a></li>
                </ul>
            </nav>
        </header>
        <table style="width: 90%">
            <tr>
                <td><label for="title-input-field">title</label></td>
                <td><input id="title-input-field" style="width: 90%;"></td>
            </tr>
            <tr>
                <td><label for="description-input-field">description</label></td>
                <td><input id="description-input-field" style="width: 90%;"></td>
            </tr>
            <tr>
                <td><label for="base-cost-input-field">Base cost</label></td>
                <td><input type="number" id="base-cost-input-field" style="width: 90%;"></td>
            </tr>
            <tr>
                <td><label for="is-hot-input-field">Is hot</label></td>
                <td><input type="checkbox" id="is-hot-input-field" style="width: 90%;"></td>
            </tr>
            <tr>
                <td><label for="sales-cost-input-field">Sales cost</label></td>
                <td><input type="number" id="sales-cost-input-field" style="width: 90%;"></td>
            </tr>
            <tr>
                <td><label for="capacity-input-field">Capacity</label></td>
                <td><input type="number" id="capacity-input-field" style="width: 90%;"></td>
            </tr>

            <tr>
                <td>
                    <button onclick="addTrip()">Add trip</button>
                </td>
            </tr>
        </table>

        <input id="prompt-input-field" oninput="search()" style="width: 100%; text-align: center"
               placeholder="Search prompt">

        <div id="content" class="site-content">
            <div id="primary" class="content-area column full">
                <main id="main" class="site-main" role="main">
                    <div id="prompt-search-results"></div>


                </main>
                <!-- #main -->
            </div>
            <!-- #primary -->
        </div>
        <!-- #content -->
    </div>
    <!-- .container -->
</div>
<!-- #page -->

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/pages/JS/trip-management.js"></script>
</body>
</html>