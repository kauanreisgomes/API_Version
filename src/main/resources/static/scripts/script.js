$.ajax({
    url: 'http://localhost:8080/programa',
    type: 'get',
    dataType: 'json',
    success: function (data) {
        var html;
        $.each(data, function(index, value){

            html += "<option value='"+value["id"]+"'>"+value["nome"]+"</option>"
            
        });
        $("#programa").html(html)

    },
    error: function(error){
        console.log(error)
    }
});

function getBase64(file) {
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function () {
      console.log(reader.result);
    };
    reader.onerror = function (error) {
      console.log('Error: ', error);
    };
 }

function isNull(teste){
    if(teste == null || teste == ""){
        return true;
    }else{
        return false;
    }
}

function toBlob(file) {
    var reader = new FileReader();
    reader.readAsDataURL(file); 
    reader.onloadend = function() {
      var base64data = reader.result;                
      console.log(base64data);
      return base64data;
    }
};

function fileToBase64(file) {
    var reader = new FileReader();
    // Read file content on file loaded event
    var value;
    
    reader.onloadend = function() {
        var b64 = reader.result;
        console.log(b64);
        file = b64.substring(b64.lastIndexOf(",")+1)
        var versao = $("#versao_1").val()+"."+$("#versao_2").val()+"."+$("#versao_3").val();
        sendVersion($("#programa").val(),versao,"1","0",file)
    };

    reader.readAsDataURL(file);
   
    // Convert data to base64 
   
    //console.log(reader.readAsDataURL(file))

};

$('#formulario-prog').on('submit', (event) => {
    event.preventDefault();
    console.log("teste");
    formulario();
});

function formulario(){
    var programa = $("#programa").val();
    var versao = $("#versao_1").val()+"."+$("#versao_2").val()+"."+$("#versao_3").val()

    if((isNull($("#versao_1").val()) || isNull($("#versao_2").val()) || isNull($("#versao_3").val()) &&  /^\d+$/.test(versao.replaceAll(".","")))){

       // alert("Digite uma versão válida!");
        
    }else if(programa == null){

        //alert("Selecione um programa!");

    }else if($('#file-prog')[0].files.length == 0){

        //alert("Selecione um arquivo!");

    }else{
        //alert(getBase64().toString())
        var file = $('#file-prog')[0].files[0];
        /*console.log(file.split('.').pop());
        if(file.split('.').pop() == ".jar"){*/
        fileToBase64(file); // Lá dentro tem é chamado o sendVersion
        /*}else{
            alert("Tipo de arquivo não permitido!");
        }*/
        
    }
}

function sendVersion(id_prog,versao,user,status,file){
    $.ajax({
        url: 'http://localhost:8080/versoes/save',
        type: 'post',
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            alert('Versão salva com sucesso!');
        },
        data: JSON.stringify( 
            {
                id_prog : $("#programa").val(),
                versao : $("#versao_1").val()+"."+$("#versao_2").val()+"."+$("#versao_3").val(),
                user : "1",
                status : "0",
                file :  file
            }
        )
    });
}