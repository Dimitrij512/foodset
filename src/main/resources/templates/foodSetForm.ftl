<html>
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

    input[type="text"],
    input[type="password"],
    input[type="date"],
    input[type="datetime"],
    input[type="email"],
    input[type="number"],
    input[type="search"],
    input[type="tel"],
    input[type="time"],
    input[type="url"],
    textarea,
    select {
        background: rgba(255, 255, 255, 0.1);
        border: none;
        font-size: 16px;
        height: auto;
        margin: 0;
        outline: 0;
        padding: 15px;
        width: 100%;
        background-color: #e8eeef;
        color: #8a97a0;
        box-shadow: 0 1px 0 rgba(0, 0, 0, 0.03) inset;
        margin-bottom: 30px;
    }

    input[type="radio"],
    input[type="checkbox"] {
        margin: 0 4px 8px 0;
    }

    select {
        padding: 6px;
        height: 32px;
        border-radius: 2px;
    }

    button {
        padding: 19px 39px 18px 39px;
        color: #FFF;
        background-color: #4bc970;
        font-size: 18px;
        text-align: center;
        font-style: normal;
        border-radius: 5px;
        width: 100%;
        border: 1px solid #3ac162;
        border-width: 1px 1px 3px;
        box-shadow: 0 -1px 0 rgba(255, 255, 255, 0.1) inset;
        margin-bottom: 10px;
    }

    fieldset {
        margin-bottom: 30px;
        border: none;
    }

    legend {
        font-size: 1.4em;
        margin-bottom: 10px;
    }

    label {
        display: block;
        margin-bottom: 8px;
    }

    label.light {
        font-weight: 300;
        display: inline;
    }

    .number {
        background-color: #5fcf80;
        color: #fff;
        height: 30px;
        width: 30px;
        display: inline-block;
        font-size: 0.8em;
        margin-right: 4px;
        line-height: 30px;
        text-align: center;
        text-shadow: 0 1px 0 rgba(255, 255, 255, 0.2);
        border-radius: 100%;
    }

    @media screen and (min-width: 480px) {

        form {
            max-width: 480px;
        }

    }
</style>

<script>
    $(document).ready(function(){
        let date_input=$('input[name="date"]'); //our date input has the name "date"
        let container=$('.bootstrap-iso').length>0 ? $('.bootstrap-iso').parent() : "body";

        let oneWeekFuture = new Date();
        oneWeekFuture.setDate(oneWeekFuture.getDate() + 7);


        date_input.datepicker({
            format: 'dd-mm-yyyy',
            container: container,
            todayHighlight: true,
            autoclose: true,
            startDate: new Date(),
            endDate: oneWeekFuture,
            daysOfWeekDisabled:[5,6,0],

            // onSelect: function (date) {
            //     $('#selectName').val(date).attr('readonly', true); // if you want to make event fire after the date selection
            // }
        })
    })
</script>

<body>

<form action="index.html" method="post">

    <h1>Реєстрація</h1>

    <fieldset>
        <legend><span class="number">1</span> Вибріть день</legend>

        <label for="name">День:</label>
        <input class="form-control" id="date" name="date" placeholder="день-місяць-рік" type="text"/>

        <label for="hour">Година:</label>
        <select id="hour" name="hour">
                <option value="14:00">14:00</option>
                <option value="15:00">15:00</option>
        </select>
    </fieldset>

    <fieldset>
        <legend><span class="number">2</span> Ваші дані</legend>

        <label for="name">Ім'я:</label>
        <input type="text" id="name" name="user_name">

        <label for="surname">Прізвище:</label>
        <input type="text" id="surname" name="user_surname">

        <label for="phone">Номер телефону:</label>
        <input type="text" id="phone" name="user_phone">

        <label for="kids_count">Кількість дітей в сім'ї:</label>
        <select id="hour" name="kids_count">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">3</option>
            <option value="5">5</option>
            <option value="6">6</option>
            <option value="7">7</option>
        </select>

        <label>Тип набору:</label>
        <input type="radio" id="food_set" value="food_set" name="set_type"><label for="food_set" class="light">Продуктовий набір</label><br>
        <input type="radio" id="complex_dinner" value="complex_dinner" name="set_type"><label for="complex_dinner" class="light">Комплексний обід</label>
    </fieldset>

    <button type="submit">Надіслати</button>
</form>

</body>

</html>
