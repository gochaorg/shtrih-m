function echo (){
  var str = ''
  for( var i=0; i<arguments.length; i++ ){
    str += arguments[i];
  }
  WScript.echo( str );
}

echo( 'test1', '2' );

try {
  echo( 'create activex object' )
  var Driver = new ActiveXObject("Addin.DRvFR");

  echo( 'connect' );
  Driver.Connect();

  echo( 'ResultCode=', Driver.ResultCode );
  echo( 'ResultCodeDescription=', Driver.ResultCodeDescription );

  Driver.StringForPrinting = "Тестовая строка";
  Driver.Price = 100;
  Driver.Quantity = 200;
  Driver.Department = 1;
  Driver.Tax1 = 0;
  Driver.Tax2 = 0;
  Driver.Tax3 = 0;
  Driver.Tax4 = 0;
  Driver.Sale();

  Driver.Password = 30;
  Driver.Summ1 = 1500;
  Driver.Summ2 = 100;
  Driver.Summ3 = 300;
  Driver.Summ4 =300;
  Driver.DiscountOnCheck = 5;

  Driver.Tax1 = 1;
  Driver.Tax2 = 2;
  Driver.Tax3 = 0;
  Driver.Tax4 = 0;
  Driver.StringForPrinting = '====================================';
  Driver.CloseCheck();
  
} catch ( e ){
  echo( 'err: ',e );
}
