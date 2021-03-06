<%-- 
    Document   : pesquisaLoja
    Created on : 22/11/2020, 17:02:45
    Author     : carol
--%>

<%@page import="java.util.List"%>
<%@page import="semAglomerar.Model.Loja"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="header.jsp" />
        <title>Lojas</title>
        <link href="css/default.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>
        <div id="cabecalho">
            <a class="left" href="index.html">
                <img src="img/logo.png" alt="Logotipo da Sem Aglomerar" width=200 height=100>
            </a>
            <form class="search-center" method="post" action="/SemAglomerar/pesquisar-loja">
                <input type="text" name="txtPesquisa" class= "barra-de-pesquisa" placeholder="Pesquisar Lojas" value=""/>
                <% String shop = (String)request.getAttribute("shop_id"); %>
                <input type="hidden" name="shop_id" value="<%= shop %>"/>   
                <a href="#">   
                    <img src="img/036-zoom.png" alt="Procurar" width=15 height=15 />
                </a>
            </form>
            <h3>Olá!</h3>
        </div>
        <div id="lateral">
            <fieldset class="filtro">
                <legend>Escolha por Categorias</legend>
                <div>
                    <input type="checkbox" id ="artigosDoLar" name="artigosDoLar">
                    <label for="artigosDoLar">Artigos do Lar</label>
                </div>
                <div>
                    <input type="checkbox" id ="alimentacao" name="alimentacao">
                    <label for="alimentacao">Alimentação</label>
                </div>
                <div>
                    <input type="checkbox" id ="vestuario" name="vestuario">
                    <label for="vestuario">Vestuário</label>
                </div>
                <div>
                    <input type="checkbox" id ="calcados" name="calcados">
                    <label for="calcados">Calçados</label>
                </div>
                <div>
                    <input type="checkbox" id ="servicosEssenciais" name="servicosEssenciais">
                    <label for="servicosEssenciais">Serviços Essenciais</label>
                </div>
                <div>
                    <input type="checkbox" id ="beleza" name="beleza">
                    <label for="beleza">Beleza</label>
                </div>
                <div>
                    <input type="checkbox" id ="outros" name="outros">
                    <label for="outros">Outros</label>
                </div>
            </fieldset>
        </div>
        <div class="lista-lojas">
            <h2>Lojas</h2>
            
            <% List<Loja> lojas = (List<Loja>) request.getAttribute("lojas"); %>
            <% for (Loja loja : lojas) { %>
                <div class="card">
                    <!-- <img src="img/lojas/cea.png" alt="Logo C&A" style="width:90px"/> -->
                    <p>
                        <span><%= loja.getNome() %></span> 
                        - <%= loja.getCategoria() %>
                    </p>
                    <p><%= loja.getLocalizacao() %></p>
                    <a class="right" href="/SemAglomerar/relatorio?loja_id=<%= loja.getId() %>">                        
                        Movimentação da Loja
                    </a>
                </div>
            <% }%>
        </div>
    </div>
    <jsp:include page="footer-fixed.jsp" />
</body>
</html>