<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        body { font-family: Arial, sans-serif; text-align: center; margin-top: 50px; }
        .container { width: 300px; margin: auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }
        input { width: 100%; padding: 8px; margin: 5px 0; }
        button { background-color: blue; color: white; padding: 10px; border: none; cursor: pointer; }
        .error { color: red; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Connexion</h2>
        <% 
            String error = request.getParameter("error");
            if (error != null) { 
        %>
            <p class="error">Email ou mot de passe incorrect !</p>
        <% } %>
        
        <form action="LoginServlet" method="post">
            <label>Email:</label>
            <input type="email" name="email" required>
            <label>Mot de passe:</label>
            <input type="password" name="mdp" required>
            <button type="submit">Se connecter</button>
        </form>
    </div>
</body>
</html>
