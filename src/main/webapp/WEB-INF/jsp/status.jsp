<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>

<layout:default title="Archiver" >
    <jsp:attribute name="content">
        <%--Place the progress bar in the webpage--%>
        <div id="progress_bar" class="ui-progress-bar ui-container">
                 <div class="ui-progress" id="bar" style="width: 0%;">
                     <span class="ui-label" >Processing <b class="value" id="process" >0%</b></span>
                     <%--<span class="ui-label" style="display:none;">Processing <b class="value">0%</b></span>--%>
                 </div>
             </div>
        <%--This is the javascript that makes recursive calls to update the status bar--%>
        <script type="text/javascript">
            <%--Setup our indicator--%>
            var isDone = false;
            <%--When we are done this is called--%>
            YAHOO.util.Connect.asyncRequest('GET', '/archiver?c', {success:function(o) {
                <%--Tell the updater that you are done--%>
                isDone = true;
                <%--Set teh status bar to 100%--%>
                document.getElementById('bar').style.width = "100%";
                document.getElementById('process').innerHTML = "100%";
                <%--Create the download window and redirect to it--%>
                window.location = '<c:url value="/archiver" />';
            }});
            function update(){
                var callback = {
                    <%--Update the status bar--%>
                    success: function(o) {
                        var jsonObject = o.responseText;
                        var prod = YAHOO.lang.JSON.parse(jsonObject);
                        var percent = (prod.currentProgess/prod.totalTweets)*100;
                        percent = Math.round(percent);
                        document.getElementById('bar').style.width = percent.toString() +'%';
                        document.getElementById('process').innerHTML = percent.toString() + "%";
                    },
                    <%--If you cannot update write out an error--%>
                    failure: function(o) {
                        if(!isDone) alert("failure");
                    }
                };
                <%--Request progress information from the progress servlet--%>
                if(!isDone) YAHOO.util.Connect.asyncRequest('GET', '/progress', callback, null);
            }
            <%--Set the delay between updating the status bar--%>
            setInterval(update,250);
        </script>

    </jsp:attribute>
</layout:default>