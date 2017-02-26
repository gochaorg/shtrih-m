//println System.getProperty( 'java.home' )
import xyz.cofe.shrtihm.*;
import xyz.cofe.shrtihm.jacob.ShtrihMJacobDriver;

/*println "create driver jacob"
jdrv = new ShtrihMJacobDriver()

println "create driver fun"
drv = new ShtrihMFun( jdrv )
*/

def res

//res = drv.connect(null,null,null,null,null,null,30)
//println "$res.code $res.description"

/*
res = drv.getECRMode()
println "$res.code $res.description"+
        "\n  mode=$res.result.mode"+
        "\n  mode8s=$res.result.mode8Status"+
        "\n  desc=$res.result.description"
*/

/*
res = drv.openCheck( CheckType.SALE, null )
println "$res.code $res.description"

res = drv.getECRMode()
println "$res.code $res.description"+
        "\n  mode=$res.result.mode"+
        "\n  mode8s=$res.result.mode8Status"+
        "\n  desc=$res.result.description"
*/

/*
res = drv.sale( 'Колбаса', (double)0.5, (long)1234567, 9, false, false, false, false, 30 )
println "$res.code $res.description"
if( res.codeSuccess ){
println "  operatorNumber= $res.result.operatorNumber"
}
*/

/*
res =
  drv.closeCheck( 
    'БлаБла', 
    (long)12334567, (long)12334567, (long)12334567, (long)12334567, 
    (double)0.0, 
    false, false, false, false, 
    30 )

println "$res.code $res.description"
if( res.codeSuccess ){
  println "  operatorNumber= $res.result.operatorNumber"
  println "          change= $res.result.change"
}
*/

res = drv.getECRMode()
println "$res.code $res.description"+
        "\n  mode=$res.result.mode"+
        "\n  mode8s=$res.result.mode8Status"+
        "\n  desc=$res.result.description"

drv.disconnect()