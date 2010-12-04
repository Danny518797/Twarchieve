<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:default title="Archiver" >
    <jsp:attribute name="content">
        <div id="progress_bar" class="ui-progress-bar ui-container">
                 <div class="ui-progress" id="bar" style="width: 0%;">
                     <span class="ui-label" >Processing <b class="value" id="process" >0%</b></span>
                     <%--<span class="ui-label" style="display:none;">Processing <b class="value">0%</b></span>--%>
                 </div>
             </div>

                <script type="text/javascript">
                    var isDone = false;
                    YAHOO.util.Connect.asyncRequest('GET', '/archiver?c', {success:function(o) {            
                        isDone = true;
                        document.getElementById('bar').style.width = "100%";
                        document.getElementById('process').innerHTML = "100%";
                        setTimeout(window.location = '<c:url value="/archiver" />',100);
                    }});
                    function update(){

                        var callback = {
                            success: function(o) {
                                var jsonObject = o.responseText;
                                var prod = YAHOO.lang.JSON.parse(jsonObject);
                                var percent = (prod.currentProgess/prod.totalTweets)*100;
                                percent = Math.round(percent);
                                document.getElementById('bar').style.width = percent.toString() +'%';
                                document.getElementById('process').innerHTML = percent.toString() + "%";
                            },
                            failure: function(o) {
                            }

                        };

                        if(!isDone) YAHOO.util.Connect.asyncRequest('GET', '/progress', callback, null);
                    }
                    setInterval(update,250);
                </script>

    </jsp:attribute>
</layout:default>