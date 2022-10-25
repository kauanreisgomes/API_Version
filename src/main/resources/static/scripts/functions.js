
function isNull(teste){
    if(teste == null || teste == ""){
        return true;
    }else{
        return false;
    }
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

function closeSlow(alert){
    setTimeout(function() {
        alert.fadeOut("slow", function(){
            $(this).hide();
        });				
    }, 5000);
}

export {isNull, sleep, closeSlow}