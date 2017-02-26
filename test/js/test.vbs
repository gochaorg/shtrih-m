  Dim Driver 
  Set Driver = CreateObject("Addin.DRvFR")
  Driver.ShowProperties()
  Driver.Connect()
  ' Точная продажа
  Driver.StringForPrinting = "Тестовая строка"
  Driver.Price = 100
  Driver.Quantity = 200
  Driver.Department = 1
  Driver.Tax1 = 0
  Driver.Tax2 = 0
  Driver.Tax3 = 0
  Driver.Tax4 = 0
  Driver.SaleEx()
  ' Просто продажа
  Driver.StringForPrinting = "Тестовая строка"
  Driver.Price = 100
  Driver.Quantity = 200
  Driver.Department = 1
  Driver.Tax1 = 0
  Driver.Tax2 = 0
  Driver.Tax3 = 0
  Driver.Tax4 = 0
  Driver.Sale()