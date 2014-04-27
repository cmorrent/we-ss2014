<%@ page import="at.ac.tuwien.big.we14.lab2.api.domain.Game" %>
<%@ page import="at.ac.tuwien.big.we14.lab2.api.Choice" %>
<%@ page import="at.ac.tuwien.big.we14.lab2.api.domain.AnswerStatus" %>
<%@ page import="java.util.List" %>
<% Game game = (Game) session.getAttribute("game"); %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Business Informatics Group Quiz</title>
        <link rel="stylesheet" type="text/css" href="style/screen.css" />
        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/framework.js" type="text/javascript"></script>
    </head>
    <body id="questionpage">
        <a class="accessibility" href="#question">Zur Frage springen</a>
        <header role="banner" aria-labelledby="mainheading"><h1 id="mainheading"><span class="accessibility">Business Informatics Group</span> Quiz</h1></header>
        <nav role="navigation" aria-labelledby="navheading">
            <h2 id="navheading" class="accessibility">Navigation</h2>
            <ul>
                <li><a id="logoutlink" title="Klicke hier um dich abzumelden" href="#" accesskey="l">Abmelden</a></li>
            </ul>
        </nav>
        
        <!-- round info -->
        <section role="main">
            <section id="roundinfo" aria-labelledby="roundinfoheading">
                <h2 id="roundinfoheading" class="accessibility">Spielerinformationen</h2>
                <div id="player1info">
                    <span id="player1name"><%= game.getPlayer1Name() %></span>
                    <ul class="playerroundsummary">
                    	<% for(int i = 0; i < game.getActualRound().getAnswers().size(); i++) { %>
                        	<li><span class="accessibility">Frage <%= i+1 %>:</span><span id="player1answer<%= i+1 %>"
                        	<% if(game.getActualRound().getAnswers().get(i).getPlayer1AnswerStatus() == AnswerStatus.answered_correct) { %>
                        		class="correct">Richtig	
                        	<% } if(game.getActualRound().getAnswers().get(i).getPlayer1AnswerStatus() == AnswerStatus.answered_failed) { %>
                        		class="incorrect">Falsch
                        	<% } if(game.getActualRound().getAnswers().get(i).getPlayer1AnswerStatus() == AnswerStatus.open) { %>
                        		class="unknown">Unbekannt
                        	<% } %>	
                        	</span></li>
                        <% } %>
                    </ul>
                </div>
                <div id="player2info">
                    <span id="player2name"><%= game.getPlayer2Name() %></span>
                    <ul class="playerroundsummary">
                        <% for(int i = 0; i < game.getActualRound().getAnswers().size(); i++) { %>
                        	<li><span class="accessibility">Frage <%= i+1 %>:</span><span id="player2answer<%= i+1 %>"
                        	<% if(game.getActualRound().getAnswers().get(i).getPlayer1AnswerStatus() == AnswerStatus.answered_correct) { %>
                        		class="correct">Richtig	
                        	<% } if(game.getActualRound().getAnswers().get(i).getPlayer1AnswerStatus() == AnswerStatus.answered_failed) { %>
                        		class="incorrect">Falsch
                        	<% } if(game.getActualRound().getAnswers().get(i).getPlayer1AnswerStatus() == AnswerStatus.open) { %>
                        		class="unknown">Unbekannt
                        	<% } %>	
                        	</span></li>
                        <% } %>
                    </ul>
                </div>
                <div id="currentcategory"><span class="accessibility">Kategorie:</span>

                    <%= game.getActualRound().getCategory().getName() %>
                </div>
            </section>
            
            <!-- Question -->
            <section id="question" aria-labelledby="questionheading">
                
                <form id="questionform" method="post" action="bigQuiz">
                    <h2 id="questionheading" class="accessibility">Frage</h2>
                    <p id="questiontext"><%= game.getActualRound().getActualAnswer().getQuestion().getText() %></p>
                    <ul id="answers">
                        <% List<Choice> choices = game.getActualRound().getActualAnswer().getQuestion().getAllChoices(); %>
                        <% for(int i = 0; i < choices.size(); i++) { %>
                        <% Choice choice = choices.get(i); %>
                            <li>
                                <input id="option<%= i %>"
                                       name="option<%= i %>"
                                       type="checkbox"
                                       value="<%= choice.getId()%>" />
                                <label for="option<%= i %>"><%= choice.getText()%></label>
                            </li>
                        <% } %>
                    </ul>
                    <input id="answer_count" name="answer_count" value="<%= choices.size()%>" type="hidden"/>
                    <input id="action" name="action" type="hidden" value="action_question" />
                    <input id="timeleftvalue" type="hidden" value="100"/>
                    <input id="question_id" name="question_id" type="hidden" value="<%= game.getActualRound().getActualAnswer().getQuestion().getId() %>"/>
                    <input id="next" type="submit" value="weiter" accesskey="s"/>
                </form>
            </section>
            
            <section id="timer" aria-labelledby="timerheading">
                <h2 id="timerheading" class="accessibility">Timer</h2>
                <p><span id="timeleftlabel">Verbleibende Zeit:</span> <time id="timeleft">00:30</time></p>
                <meter id="timermeter" min="0" low="20" value="100" max="100"/>
            </section>
            
            <section id="lastgame">
                <p>Letztes Spiel: <span id="lastgameTimestamp">Nie</span></p>
            </section>
        </section>

        <!-- footer -->
        <footer role="contentinfo">© 2014 BIG Quiz</footer>
        
        <script type="text/javascript">
            //<![CDATA[
            
            // initialize time
            $(document).ready(function() {
                var maxtime = 30;
                var hiddenInput = $("#timeleftvalue");
                var meter = $("#timer meter");
                var timeleft = $("#timeleft");
                
                hiddenInput.val(maxtime);
                meter.val(maxtime);
                meter.attr('max', maxtime);
                meter.attr('low', maxtime/100*20);
                timeleft.text(secToMMSS(maxtime));
                
                if(supportsLocalStorage()) {
                    if(localStorage.lastgameTimestamp != null) {
                        $("#lastgameTimestamp").text(localStorage.lastgameTimestamp);
                    }
                } else {
                    $("#lastgameTimestamp").text("Nie");
                }
            });
            
            // update time
            function timeStep() {
                var hiddenInput = $("#timeleftvalue");
                var meter = $("#timer meter");
                var timeleft = $("#timeleft");
                
                var value = $("#timeleftvalue").val();
                if(value > 0) {
                    value = value - 1;   
                }
                
                hiddenInput.val(value);
                meter.val(value);
                timeleft.text(secToMMSS(value));
                
                if(value <= 0) {
                    $('#questionform').submit();
                }
            }
            
            window.setInterval(timeStep, 1000);
            
            //]]>
        </script>
    </body>
</html>
