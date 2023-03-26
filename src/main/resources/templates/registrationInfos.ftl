<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<#--    <!-- Bootstrap Date-Picker Plugin &ndash;&gt;-->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.12.4.js"></script>
<script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!------ Include the above in your HEAD tag ---------->

<style>
    .filterable {
        margin-top: 15px;
    }

    .filterable .panel-heading .pull-right {
        margin-top: -20px;
    }

    .filterable .filters input[disabled] {
        background-color: transparent;
        border: none;
        cursor: auto;
        box-shadow: none;
        padding: 0;
        height: auto;
    }

    .filterable .filters input[disabled]::-webkit-input-placeholder {
        color: #333;
    }

    .filterable .filters input[disabled]::-moz-placeholder {
        color: #333;
    }

    .filterable .filters input[disabled]:-ms-input-placeholder {
        color: #333;
    }
</style>

<script>

    function removeItem(registrationInfoId) {
        $.ajax({
            url: "/registration-infos/" + registrationInfoId,
            type: 'DELETE'
        });

        document.getElementById(registrationInfoId).closest('tr').remove();
    }

    $(document).ready(function () {

        $('#generate-xls').click(function () {
            let startDate = $('#date-from').val();
            let endDate = $('#date-to').val();
            window.location.href="/registration-infos/generate-excel-file/?startDate=" + startDate + "&endDate=" + endDate;
        });

        $('#create-registration-info').click(function () {
            window.location.href='/registration-infos/create/free';
        });

        $('#configuration-info').click(function () {
            window.location.href='/configuration';
        });

        document.getElementById('date-from').value = new Date().toISOString().split('T')[0];
        document.getElementById('date-to').value = new Date().toISOString().split('T')[0];

        $('tbody').on('click', 'button', function() {

            let saveButton = $(this).closest('tr').find('.reg-info-row-save');
            let editButton = $(this).closest('tr').find('.reg-info-row-edit');

            //edit
            if(saveButton.is(':disabled')) {
                $(this).closest('tr').find('.is-receive-row-select').prop("disabled", false)
                $(this).prop("disabled", true).css('color', 'gray');
                saveButton.prop("disabled", false).css('color', 'green');
            // save
            } else {
                let isReceive =  $(this).closest('tr').find('.is-receive-row-select').find(":selected").text();
                let registrationInfoId =  $(this).closest('tr').find('.info-id').text();

                $(this).closest('tr').find('.is-receive-row-select').prop("disabled", true);
                $(this).prop("disabled", true).css('color', 'gray');
                editButton.prop("disabled", false).css('color', 'green');

                $.ajax({
                    type: "PUT",
                    url: "/registration-infos/" + registrationInfoId,
                    accept: 'application/json',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        received: "" + isReceive + "" ,
                        comment: ""
                    })
                });
            }
        });

        var dateFormat = "yy-mm-dd",
            from = $( "#date-from")
                .datepicker({
                    dateFormat: "yy-mm-dd",
                    defaultDate: "+1w",
                    monthNames: ["Січень", "Лютий", "Березень", "Квітень", "Травень", "Червень", "Липень", "Серпень", "Вересень", "Жовтень", "Листопад", "Грудень"],
                    dayNamesMin: ["Нд", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"],
                    numberOfMonths: 1
                })
                .on( "change", function() {
                    to.datepicker( "option", "minDate", getDate( this ) );
                }),
            to = $( "#date-to").datepicker({
                dateFormat: "yy-mm-dd",
                monthNames: ["Січень", "Лютий", "Березень", "Квітень", "Травень", "Червень", "Липень", "Серпень", "Вересень", "Жовтень", "Листопад", "Грудень"],
                dayNamesMin: ["Нд", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"],
                defaultDate: "+1w",
                numberOfMonths: 1
            })
                .on( "change", function() {
                    from.datepicker( "option", "maxDate", getDate( this ) );
                });

        function getDate( element ) {
            return $.datepicker.parseDate( dateFormat, element.value );
        }


        $('#date-to').datepicker().change("changeDate", function() {
            let startDate = $('#date-from').val();
            let endDate = $('#date-to').val();
            $.get("/registration-infos-content-by-range/?startDate=" + startDate + "&endDate=" + endDate, function (response) {

                $(".reg-info-table-body").empty();
                $(response).each(function(index, refugeesInfo) {
                   index = index + 1;
                   let html =
                        '<tr id="'+ refugeesInfo.id +'">'
                        + '<td class="reg-info-index">' + index + '</td>'
                        + '<td>' + refugeesInfo.receiveDate + '</td>'
                        + '<td>' + refugeesInfo.stream + '</td>'
                        + '<td>' + refugeesInfo.phoneNumber + '</td>'
                        + '<td>' + refugeesInfo.phoneNumberMessenger + '</td>'
                        + '<td>' + refugeesInfo.surname + '</td>'
                        + '<td>' + refugeesInfo.name + '</td>'
                        + '<td>' + refugeesInfo.kidsCount + '</td>'
                        + '<td>' + refugeesInfo.categoriesAssistance + '</td>'
                        + '<td>' + refugeesInfo.typeSet + '</td>'
                        + '<td class="is-receive-row">'
                              + '<select disabled class="is-receive-row-select">'
                                  + '<option selected>' + refugeesInfo.receive + '</option>'
                                  + '<option>Так</option>'
                                  + '<option>Ні</option>'
                              + '</select>'
                        + '</td>'
                        + '<td class="info-id" style="display: none">' + refugeesInfo.id + '</td>'
                        + '<td><button id="edit" class="btn btn-default reg-info-row-edit" style="color:green;""><span class="glyphicon glyphicon-edit"></span></button></td>'
                        + '<td><button id="save" class="btn btn-default reg-info-row-save" disabled><span class="glyphicon glyphicon-saved"></span></button></td>'
                        + '<td><button id="remove" onclick=removeItem("'+refugeesInfo.id+'") class="btn btn-default reg-info-row-remove"><span class="glyphicon glyphicon-remove"></span></button></td>' +
                       '</tr>';

                    $(".reg-info-table-body").append(html);
                });
            });
        });

        $('.filterable .btn-filter').click(function () {
            var $panel = $(this).parents('.filterable'),
                $filters = $panel.find('.filters input'),
                $tbody = $panel.find('.table tbody');
            if ($filters.prop('disabled') == true) {
                $filters.prop('disabled', false);
                $filters.first().focus();
            } else {
                $filters.val('').prop('disabled', true);
                $tbody.find('.no-result').remove();
                $tbody.find('tr').show();
            }
        });

        $('.filterable .filters input').keyup(function (e) {
            /* Ignore tab key */
            let code = e.keyCode || e.which;
            if (code == '9') return;
            /* Useful DOM data and selectors */
            let $input = $(this),
                inputContent = $input.val().toLowerCase(),
                $panel = $input.parents('.filterable'),
                column = $panel.find('.filters th').index($input.parents('th')),
                $table = $panel.find('.table'),
                $rows = $table.find('tbody tr');
            /* Dirtiest filter function ever ;) */
            let $filteredRows = $rows.filter(function () {
                var value = $(this).find('td').eq(column).text().toLowerCase();
                return value.indexOf(inputContent) === -1;
            });
            $table.find('tbody .no-result').remove();
            $rows.show();
            $filteredRows.hide();
            if ($filteredRows.length === $rows.length) {
                $table.find('tbody').prepend($('<tr class="no-result text-center"><td colspan="' + $table.find('.filters th').length + '">Нічого не знайдено</td></tr>'));
            }
        });
    });
</script>

<div class="container">
    <div class="row">
        <div class="panel panel-primary filterable">
            <div class="panel-heading">
                <h3 class="panel-title">Інформація про реєстрацію </h3>

                <div class="pull-right">
                    <button class="btn btn-default btn-xs btn-generate-xls" id="generate-xls"><span class="glyphicon glyphicon-file"></span>
                        xls
                    </button>
                </div>
                <div class="pull-right" style="margin-right: 2px">
                    <button class="btn btn-default btn-xs btn-filter"><span class="glyphicon glyphicon-filter"></span>
                        Фільтр
                    </button>
                </div>

                <div class="pull-right" style="margin-right: 2px">
                    <button class="btn btn-default btn-xs" id="create-registration-info"><span class="glyphicon glyphicon-plus"></span>
                        Додати
                    </button>
                </div>
                <div class="pull-right" style="margin-right: 2px">
                    <button class="btn btn-default btn-xs" id="configuration-info"><span class="glyphicon glyphicon-flash"></span>
                        Налаштувати
                    </button>
                </div>
                <div class="pull-right input-group" style="margin-right: 2px">
                    <input class="btn btn-default btn-xs"  id="date-to" name="date-to" placeholder="дата до" type="text">
                </div>
                <div class="pull-right input-group" style="margin-right: 2px">
                    <input class="btn btn-default btn-xs"  id="date-from" name="date-from" placeholder="дата від" type="text">
                </div>

            </div>
            <table class="table" id="infos-table-id">
                <thead>
                <tr class="filters">
                    <th><input type="text" class="form-control" placeholder="№" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Дата" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Потік" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Телефон" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Messanger" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Фамілія" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Ім'я" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Члени сім'ї" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Категорія" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Тип" disabled></th>
                    <th><input type="text" class="form-control" placeholder="Видано" disabled></th>
                </tr>
                </thead>
                <tbody class="reg-info-table-body">
                <#list registrationInfos as registrationInfo>
                    <tr id=${registrationInfo.id}>
                        <td class="reg-info-index">${registrationInfo?index}</td>
                        <td>${registrationInfo.receiveDate}</td>
                        <td>${registrationInfo.stream}</td>
                        <td>${registrationInfo.phoneNumber}</td>
                        <td>${registrationInfo.phoneNumberMessenger}</td>
                        <td>${registrationInfo.surname}</td>
                        <td>${registrationInfo.name}</td>
                        <td>${registrationInfo.kidsCount}</td>
                        <td>${registrationInfo.categoriesAssistance}</td>
                        <td>${registrationInfo.typeSet}</td>
                        <td class='is-received-row'>
                            <select disabled  class="is-receive-row-select">
                                <option selected>${registrationInfo.receive}</option>
                                <option>Так</option>
                                <option>Ні</option>
                            </select>
                        </td>
                        <td class='info-id' style="display:none">${registrationInfo.id}</td>
                        <td><button id="edit" class="btn btn-default reg-info-row-edit" style= color:green><span class="glyphicon glyphicon-edit"></span></button></td>
                        <td><button id="save" class="btn btn-default reg-info-row-save" disabled><span class="glyphicon glyphicon-saved"></span></button></td>
                        <td><button id="remove" onclick="removeItem('${registrationInfo.id}')" class="btn btn-default reg-info-row-remove"><span class="glyphicon glyphicon-remove"></span></button></td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>