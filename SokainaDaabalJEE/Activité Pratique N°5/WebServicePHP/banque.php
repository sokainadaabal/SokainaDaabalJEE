<?php
 $mt=0; $res=0;
if(isset($_POST['action'])){
 $action=$_POST['action'];
 if($action=="OK") {
     if (isset($_POST['montant'])) {
         $mt = $_POST['montant'];
         $clientSAOP = new SoapClient("http://localhost:8080/?wsdl");
         $param = new stdClass();
         $param->montant = $mt;
         $res = $clientSAOP->__soapCall("Convert", array($param));
         $res=$res->return;
     }
 }
 elseif ($action=="Comptes") {
     $clientSAOP = new SoapClient("http://localhost:8080/?wsdl");
     $Compte=$clientSAOP->__soapCall("listCompte", array());
  }
}
?>


<html>
    <body>
        <form action="banque.php" method="post">
           Montant : <input type="text" name="montant" value="<?php echo($mt) ?>">
            <input name="action" type="submit" value="OK">
            <input name="action" type="submit" value="Comptes">
        </form>
        Résultat <br>
        <?php if(isset($res)) {?>
         <?php echo($mt) ?> en Euro = <?php echo $res ?> en DH
        <?php }?>
        <br>
        Liste des comptes
        <br>
        <?php if(isset($Compte)) {?>
            <table border="1">
                <tr>
                    <th>Code</th>
                    <th>Solde</th>
                </tr>
                <?php foreach ($Compte->return as $cp) {?>
                    <tr>
                        <td><?php echo ($cp->code) ?></td>
                        <td> <?php echo ($cp->solode) ?></td>
                    </tr>
                <?php }?>
            </table>

        <?php }?>
    </body>
</html>