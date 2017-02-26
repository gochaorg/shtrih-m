import xyz.cofe.shrtihm.*;
import xyz.cofe.shrtihm.jacob.ShtrihMJacobDriver;

//println "create driver jacob"
//jdrv = new ShtrihMJacobDriver()

//println "create driver fun"
//fdrv = new ShtrihMFun( jdrv )

def res

//res = fdrv.connect(null,null,null,null,null,null,30)
//println "last result: $res.code $res.description"

res = fdrv.getECRMode()
println "last result: $res.code $res.description";
println " ecr: ${res.result}";
//        "\n  mode=$res.result.mode"+
//        "\n  mode8s=$res.result.mode8Status"+
//        "\n  desc=$res.result.description"+
//       "\n  amode=$res.result.advancedMode"+
//        "\n  adesc=$res.result.advancedModeDescription"

res = jdrv.getECRStatus();
println "last result: $res"

println "useReceiptRibbon = $jdrv.useReceiptRibbon"
println "useJournalRibbon = $jdrv.useJournalRibbon"
println "stringQuantity   = $jdrv.stringQuantity"

//jdrv.stringForPrinting = 'test print str'
//jdrv.password = 30
//jdrv.printString()

//jdrv.stringQuantity = 5
//jdrv.feedDocument()

fdrv.disconnect()