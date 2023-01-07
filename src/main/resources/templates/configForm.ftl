<!DOCTYPE html>
<html lang="en">
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
    <#--    <!-- Bootstrap Date-Picker Plugin &ndash;&gt;-->
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="//code.jquery.com/jquery-1.12.4.js"></script>
    <script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
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

    .configuration-form {
        max-width: 500px;
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
    $(document).ready(function () {
        let availableDates = [];

        $.get("/configuration/available-dates", function(data){
            console.log("RESPONSE: ", data)
            availableDates = data;
            printAvailableDatesHTML();
        });

        function deleteDate(date) {
            availableDates = availableDates.filter(function(item) {
                return item !== date
            })
            printAvailableDatesHTML();
        }

        function printAvailableDatesHTML() {
            $(".available-dates-info").empty();
            $(availableDates).each(function (index, date) {
                let html =
                    '<li class="li-date">' + date +
                    '<button class="btn btn-default btn-sm delete-date-button" style="padding-top: 13px; width:90px; color:red">видалити</button>' +
                    '<p hidden class="p-date">' + date + '</p' +
                    '</li>'
                $(".available-dates-info").append(html);
            });
        }

        $('#save-available-dates').click(function () {
            updateAvailableDates();
        });

        function updateAvailableDates() {
            console.log("AVAILABLE DATES FOR UPDATING:", availableDates)
            $.ajax({
                type: "PUT",
                url: "/configuration/available-dates",
                accept: 'application/json',
                contentType: 'application/json',
                data: JSON.stringify({
                    availableDates: availableDates
                })
            });
        }

        $('.available-dates-info').on('focus', 'button', function() {
            let date = $(this).closest('li').find('.p-date').text()
            deleteDate(date);
        });

        $('#date').datepicker({
            showAnim: "fold",
            minDate: new Date(),
            firstDay: 1,
            startDate: new Date(),
            monthNames: ["Січень", "Лютий", "Березень", "Квітень", "Травень", "Червень", "Липень", "Серпень", "Вересень", "Жовтень", "Листопад", "Грудень"],
            dayNamesMin: ["Нд", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"],
            dateFormat: "yy-mm-dd",
            beforeShowDay: $.datepicker.noWeekends,
            onSelect: function (dateText) {
                availableDates.push(dateText);
                printAvailableDatesHTML();
            }
        });
    });
</script>

<body>
<div class="configuration-form">

    <h1>Налаштування</h1>

    <fieldset>
        <legend>Виберіть дні для роздачі допомоги</legend>
        <label for="date">День:</label>
        <input id="date" name="availableDate"
               type="text" readonly
               placeholder="дд-мм-рр"/>
    </fieldset>

    <strong>Вибрані дні роздачі</strong>
    <div class="available-dates-info"></div>
    <button id="save-available-dates">Зберегти</button>

</div>
</body>
</html>