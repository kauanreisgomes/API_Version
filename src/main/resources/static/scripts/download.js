import * as funcoes from './functions.js';

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
        $("#programas").html(html)
        showPage();
       
       // $("#loader").css("display","no");
    },
    error: function(error){
        console.log(error)
        $("#alert-danger").show()
        $("#alert-danger").text("Erro ao trazer as opções");
        funcoes.closeSlow($("#alert-danger"))
    }
});

function showPage(){
    $("#form").show();
    $("#loader").hide();
}

$("#form").on('submit',function(event){
    event.preventDefault();
    $("#button").hide();
    $("#button_load").show();
    $("#programas").attr("disabled", true);
    funcoes.sleep(5000);
    download();
});

function download(){
    /*var link = "http://localhost:8080/download/"+$("#programas option:selected").text();
    link.click();*/
    $.ajax({
        url: "http://localhost:8080/download/"+$("#programas option:selected").text(),
        method: 'GET',
        xhrFields: {
            responseType: 'blob'
        },
        beforeSend: function(){
            console.log("teste");
        },

        success: function (data) {
            var a = document.createElement('a');
            var url = window.URL.createObjectURL(data);
            a.href = url;
            a.download = $("#programas option:selected").text()+'.jar';
            document.body.append(a);
            a.click();
            a.remove();
            window.URL.revokeObjectURL(url);
            $("#alert-sucess").text("Programa baixado com sucesso!")
            $("#alert-sucess").show();
            funcoes.closeSlow($("#alert-sucess"))
            $("#button").show();
            $("#button_load").hide();
            $("#programas").attr("disabled", false);
        },
        error: function(error){
            console.log(error);
            $("#alert-danger").text("Erro ao fazer download do programa!")
            $("#alert-danger").show();
            funcoes.closeSlow($("#alert-danger"))
            $("#button").show();
            $("#button_load").hide();
            $("#programas").attr("disabled", false);
        }
    });
}
