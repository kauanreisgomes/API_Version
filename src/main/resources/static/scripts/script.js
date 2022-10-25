import * as funcoes from "./functions.js";

$.ajax({
    url: 'http://localhost:8080/programa',
    type: 'get',
    dataType: 'json',
    beforeSend: function () {
        //Aqui adicionas o loader
        $("#loader").show();
    },
    success: function (data) {
        var html;
        $.each(data, function(index, value){

            html += "<option name='"+value['nome']+"' value='"+value["id"]+"'>"+value["nome"]+"</option>"
            
        });
        $("#programa").html(html)
        showPage();
       // $("#loader").css("display","no");
    },
    error: function(error){
        console.log(error)
        $("#alert-danger").show()
        $("#alert-danger").text("Erro ao trazer as opções");
    }
});

function showPage(){
    $("#loader").hide();
    $("#formulario-prog").show();

}




$('#formulario-prog').on('submit', (event) => {
    event.preventDefault();
    //console.log($( "#programa option:selected" ).text());
    $("#enviar_load").show();
    $("#enviar").hide();
    formulario();
   // $("#enviar").html("<button type='submit' class='btn btn-primary' id='enviar'>Enviar</button>");
});

function formulario(){
    var programa = $("#programa").val();
    var versao = $("#versao_1").val()+"."+$("#versao_2").val()+"."+$("#versao_3").val();
    
    sendVersion(programa,versao,1,0);
    
}

function sendVersion(id_prog,versao,user,status){
    $.ajax({
        url: 'http://localhost:8080/versoes/save',
        type: 'post',
        //dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify( 
            {
                id_prog : $("#programa").val(),
                versao : $("#versao_1").val()+"."+$("#versao_2").val()+"."+$("#versao_3").val(),
                user : "1",
                status : "0"
            }
        ),
        success: function (data) {
            sendFile();
            //alert("Versão salva com sucesso!");
            setInterval('location.reload()', 1000); 
            $('#alert-sucess').text('Versão salva com sucesso!');
            $('#alert-sucess').alert('show');
            funcoes.closeSlow($('#alert-sucess'));
           
        },
        
        error: function(error){
            console.log(error)
            $("#alert-danger").alert('show')
            $("#alert-danger").text("Erro ao salvar a versão!");
            funcoes.closeSlow($('#alert-danger'));

            $("#enviar_load").hide();
            $("#enviar").show();
            $("#formulario-prog").attr("disabled",false);
        }
    })

}

function sendFile(file){
    //var formdata = new FormData($("form[id='formulario-prog']")[0]);
    var data = new FormData();
    data.append('file',$("#file")[0].files[0])
    $.ajax({
        url: 'http://localhost:8080/upload/'+$( "#programa option:selected" ).text(),
        type: 'post',
        method: 'POST',
        processData: false,
        contentType: false,
        async: false,
        cache: false,
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        data: data,
        success: function(){
            $("#enviar_load").hide();
            $("#enviar").show();
            $("#formulario-prog").attr("disabled",false);
        },
        error: function(error){
            console.log(error),
            //console.log("Deu erro ao enviar o arquivo")
            $("#alert-warning").alert('show')
            $("#alert-warning").text("Erro ao enviar o arquivo!");
            funcoes.closeSlow($('#alert-warning'));

            $("#enviar_load").hide();
            $("#enviar").show();
            $("#formulario-prog").attr("disabled",false);
        }
              
    });
}

