<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>


<layout:default title="StreamSavr Auth Request" >
    <jsp:attribute name="content">
             <div id="progress_bar" class="ui-progress-bar ui-container">
         <div class="ui-progress" id="lawl" style="width: 80%;">
             <span class="ui-label" style="display:none;">Processing <b class="value">79%</b></span>
         </div>
     </div>

        <script type="text/javascript">

            var callback = {
                success: function(o) {
                    alert(o.responseText);
                },
                failure: function(o) {
                    alert("failure");
                }
                //argument: [argument1, argument2, argument3]

            };

            var progress = YAHOO.util.Connect.asyncRequest('GET', '/progress', callback, null);
            var y = 0;

            function whatToDo(){

                    var callback = {
                    success: function(o) {
                        y = y +1;
                document.getElementById('lawl').style.width = y.toString() +'%';
                                            //document.getElementById('lawl').style.width = '100%';
                        //alert(x.toString + '%')  ;
                        //alert(o.responseText);
                        //document.getElementById('lawl').style.width = ;
                    },
                    failure: function(o) {
                        alert("failure");
                    }
                    //argument: [argument1, argument2, argument3]

                };

                var progress = YAHOO.util.Connect.asyncRequest('GET', '/progress', callback, null);


            }
            var x = 0;
            var t;
            while(x<100){
                //x = x +1;
                //document.getElementById('lawl').style.width = x.toString() +'%';
                t = setTimeout("whatToDo()",1000);
            }


        </script>

        



      </jsp:attribute>
</layout:default>

