<html lang="u">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
    <#--    <!-- Bootstrap Date-Picker Plugin &ndash;&gt;-->
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
    <title>Заповніть форму</title>
</head>
<style>
    *, *:before, *:after {
        -moz-box-sizing: border-box;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
    }

    body {
        font-family: 'Nunito', sans-serif;
        color: #384047;
    }

    form {
        max-width: 300px;
        margin: 10px auto;
        padding: 10px 20px;
        background: #f4f7f8;
        border-radius: 8px;
    }

    h1 {
        margin: 0 0 30px 0;
        text-align: center;
    }

    input,
    textarea,
    select {
        border: 2px solid transparent;
        height: auto;
        outline: 0;
        padding: 5px 10px;
        font-size: 14px;
        font-weight: 400;
        width: 100%;
        background-color: #e8eeef;
        color: #8a97a0;
        margin: 0 0 15px;
        /*border-radius: 4px 4px 0 0;*/
        border-radius: 4px;
        transition: 300ms all ease;
    }

    input:focus, input:active, input:hover,
    textarea:focus, textarea:active, textarea:hover,
    select:focus, select:active, select:hover {
        border-color: #ccd9db;
    }

    input:focus, input:active,
    textarea:focus, textarea:active,
    select:focus, select:active {
        box-shadow: 0 0 0 3px rgba(204, 217, 219, 0.5);
    }

    input[type="radio"],
    input[type="checkbox"] {
        margin: 0 4px 8px 0;
    }

    select {
        padding: 6px;
        height: 31px;
        cursor: pointer;
    }

    button {
        padding: 4px 10px;
        color: #FFF;
        background-color: #3ac162;
        font-size: 16px;
        text-align: center;
        font-style: normal;
        border-radius: 4px;
        width: 100%;
        box-sizing: border-box;
        border: 2px solid #3ac162;
        margin-bottom: 10px;
        transition: 300ms all ease;
    }

    button:hover, button:focus, button:active {
        background-color: transparent;
        color: #3ac162;
    }

    button:focus, button:active {
        box-shadow: 0 0 0 3px rgba(58, 193, 98, 0.5);
        outline: none;
    }

    fieldset {
        margin-bottom: 30px;
        border: none;
    }

    legend {
        display: flex;
        align-items: center;
        font-size: 23px;
        margin-bottom: 20px;
    }

    label {
        display: block;
        margin-bottom: 5px;
        font-size: 16px;
    }

    label.light {
        display: flex;
        align-items: center;
        position: relative;
    }

    label.light input {
        opacity: 0;
        position: absolute;
        top: -1000%;
        left: -1000%;
        width: 0;
        height: 0;
    }

    label.light .checkbox-container {
        width: 20px;
        height: 20px;
        border-radius: 50%;
        border: 2px solid #ccd9db;
        display: flex;
        position: relative;
        margin-right: 10px;
        box-sizing: border-box;
        transition: 300ms all ease;
    }

    label.light .checkbox-container:after {
        position: absolute;
        content: '';
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%) scale(0);
        height: 10px;
        width: 10px;
        background: #94b0b5;
        border-radius: 50%;
        transition: 300ms all ease;
    }

    label.light input:checked + .checkbox-container:after {
        transform: translate(-50%, -50%) scale(1);
    }

    label.light input:focus + .checkbox-container, label.light input:active + .checkbox-container {
        box-shadow: 0 0 0 3px rgba(204, 217, 219, 0.5);
    }

    .number {
        background-color: #5fcf80;
        color: #fff;
        height: 30px;
        width: 30px;
        font-size: 20px;
        line-height: 1.25;
        border-radius: 50%;
        margin-right: 15px;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    @media screen and (min-width: 480px) {

        form {
            max-width: 480px;
        }

    }
</style>

<script>
    $(document).ready(function(){
        let date_input=$('input[name="receiveDate"]');
        let container=$('.bootstrap-iso').length>0 ? $('.bootstrap-iso').parent() : "body";

        let oneWeekFuture = new Date();
        oneWeekFuture.setDate(oneWeekFuture.getDate() + 7);

        date_input.datepicker({
            format: 'yyyy-mm-dd',
            container: container,
            todayHighlight: true,
            autoclose: true,
            startDate: new Date(),
            endDate: oneWeekFuture,
            daysOfWeekDisabled:[6,0],
        })

        $('#date').datepicker().on("changeDate", function() {
            console.log("changeDate")
            let selectedDate = $('#date').val();
            $.get("/registration-infos/stream-of-delivery/?receiveDate=" + selectedDate, function (response) {

                if(!$.trim(response)) {
                    $('#registration-closed').removeAttr('hidden');
                    $('#stream-of-delivery').attr("hidden",true);
                    $('#user-info').attr("hidden",true);

                } else {
                    $("#time").empty()
                    response.forEach(function(streamOfDelivery) {
                        $("#time").append($('<option>', {value: streamOfDelivery, text: streamOfDelivery}));
                    })
                    $('#registration-closed').attr("hidden",true);
                    $('#stream-of-delivery').removeAttr('hidden');
                    $('#user-info').removeAttr('hidden');
                }
            });
        });

        $('#submit-registration-form').submit(function() {
            $('#registration-closed').attr("hidden",true);
            $('#error-notification').attr("hidden",true);
        });
    })

    function phoneFormat(input) {
        input = input.replace(/\D/g,'');
        let size = input.length;
        if (size>3) {input=input.slice(0,3)+" "+input.slice(3,12)}
        if (size>6) {input=input.slice(0,7)+" " +input.slice(7)}

        return input;
    }
</script>

<body>

<form id="submit-registration-form" action="/registration-infos/create" method="post">

    <h1>Реєстрація</h1>

    <#if error??>
        <div id="error-notification">
            <h5 class="text-danger">Помилка реєстрації: </h5>
            <pre class="text-danger">${error}</pre>
        </div>
    </#if>

    <fieldset>
        <legend><span class="number">1</span> Вибріть день</legend>

        <label for="date">День:</label>
        <input id="date" name="receiveDate" placeholder="дд-мм-рр" type="text"
               value="<#if registrationInfo??>${registrationInfo.receiveDate}<#else></#if>"/>
    <div id="stream-of-delivery" hidden>
        <label for="time">Година:</label>
        <select id="time" name="stream">
                <option selected value="<#if registrationInfo??>${registrationInfo.stream}<#else></#if>"/></option>
        </select>
    </div>
    <div id="registration-closed" hidden>
        <div>
            <h5 class="text-danger">На дану дату реєстрація закрита </h5>
        </div>
    </div>
    </fieldset>

    <div id="user-info" hidden>
    <fieldset>
        <legend><span class="number">2</span> Ваші дані</legend>

        <label for="surname">Прізвище:</label>
        <input type="text" id="surname" name="surname" value="<#if registrationInfo??>${registrationInfo.surname}<#else></#if>">

        <label for="name">Ім'я:</label>
        <input type="text" id="name" name="name" value="<#if registrationInfo??>${registrationInfo.name}<#else></#if>">

        <label for="phone_number">Телефон +48:</label>

        <input type="text" id="phone_number" name="phoneNumber" placeholder="телефон Польща" oninput="this.value=phoneFormat(this.value)" value="<#if registrationInfo??>${registrationInfo.phoneNumber}<#else></#if>">

        <label for="phone_number_messenger">Телефон +:</label>
        <input type="text" id="phone_number_messenger" name="phoneNumberMessenger" placeholder="телефон Viber, Whatsapp, Messenger, Telegram..." oninput="this.value=phoneFormat(this.value)" value="<#if registrationInfo??>${registrationInfo.phoneNumberMessenger}<#else></#if>">

        <label for="email">електронна пошта:</label>
        <input type="email" id="email" name="email" placeholder="ваша електронна пошта" value="<#if registrationInfo??>${registrationInfo.email}<#else></#if>">

        <label for="kids_count">Кількість членів сім'ї:</label>
        <select id="kids_count" name="kidsCount">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
            <option value="6">6</option>
            <option value="7">7</option>
        </select>

        <label>Тип набору:</label>
        <label for="food_set" class="light">
            <input type="radio" id="food_set" value="FOOD_SET" name="typeSet">
            <div class="checkbox-container"></div>
            Продуктовий набір
        </label>
        <label for="complex_dinner" class="light">
            <input type="radio" id="complex_dinner" value="COMPLEX_DINNER" name="typeSet">
            <div class="checkbox-container"></div>
            Комплексний обід
        </label>
    </fieldset>
    <button type="submit">Надіслати</button>

    </div>
</form>

</body>

</html>
