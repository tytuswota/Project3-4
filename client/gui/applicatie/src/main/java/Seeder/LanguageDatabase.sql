create table Translations(id VARCHAR(100), nederlands VARCHAR(100), english VARCHAR(100), russian VARCHAR(100));
ALTER TABLE Translations CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci;
insert into Translations values
("other","anders","other","другой"),
("menu","menu","menu","меню"),
("goBack","druk # om terug te gaan","type # to return","qwertyuiopasdfghjkl"),
("desiredAmount","voer het gewenst bedrag in","enter the desired amount","введите # для возврата"),
("backspace","backspace","backspace","бекспейс"),
("confirm","bevestigen","confirm","подтверждения"),
("balance","saldo","balance", "баланс"),
("quit","afbreken","quit", "прекращать"),
("withdraw","pinnen","withdraw","qwertyuiodfghjkl;"),
("withdraw70","₽ 70 pinnen","₽ 70 withdraw", "₽ 70 изымать"),
("cardOut","neem uw pas uit","take your card out", "вытащи свою карточку"),
("cardIn","voer uw pas in","insert your card","вставьте свою карту"),
("noConnection","Sorry, geen verbinding","Sorry, no connection", "Извините, нет связи"),
("mainMenu", "hooftmenu","mainmenu", "главное меню"),
("yourBalance","uw saldo is:","your balance is:", "qwertyuiop"),
("enterPin", "voer uw pincode in", "enter your pin", "ваш баланс:") ;
