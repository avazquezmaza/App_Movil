xquery version "2004-draft";
(:: pragma bea:global-element-return element="ns0:HeaderIn" location="../schemas/XMLSchema_415501769.xsd" ::)

declare namespace ns0 = "http://telefonica.com/globalIntegration/header";
declare namespace xf = "http://tempuri.org/APP_Movil/Resources/Common/xquery/headerFSRequest/";

declare function xf:headerFSRequest($country as xs:string,
    $lang as xs:string,
    $entity as xs:string,
    $system as xs:string,
    $originator as xs:string,
    $userID as xs:string,
    $operation as xs:string,
    $destination as xs:string,
    $timestamp as xs:string,
    $userValue as xs:string,
    $passwordValue as xs:string,
    $chanelValue as xs:string)
    as element(ns0:HeaderIn) {
        <ns0:HeaderIn>
          <ns0:country>{$country}</ns0:country>
         <ns0:lang>{$lang}</ns0:lang>
         <ns0:entity>{$entity}</ns0:entity>
         <ns0:system>{$system}</ns0:system>
         <ns0:subsystem>{$system}</ns0:subsystem>
         <ns0:originator>{$originator}</ns0:originator>
         <ns0:userId>{$userID}</ns0:userId>
         <ns0:operation>{$operation}</ns0:operation>
         <ns0:destination>{$originator}</ns0:destination>
         <ns0:execId>{fn-bea:uuid()}</ns0:execId>
         <ns0:timestamp>{fn:current-dateTime()}</ns0:timestamp>
        <ns0:varArg>
         <!--1 to 10 repetitions:-->
            <ns0:arg>
               <ns0:key>User</ns0:key>
               <ns0:values>
                  <ns0:value>{$userValue}</ns0:value>
               </ns0:values>
            </ns0:arg>
            <ns0:arg>
               <ns0:key>Password</ns0:key>
               <ns0:values>
                  <ns0:value>{$passwordValue}</ns0:value>
               </ns0:values>
            </ns0:arg>
            <ns0:arg>
               <ns0:key>Channel</ns0:key>
               <ns0:values>
                 <ns0:value>{$chanelValue}</ns0:value>
               </ns0:values>
            </ns0:arg>
         </ns0:varArg>
        </ns0:HeaderIn>
};

declare variable $country as xs:string external;
declare variable $lang as xs:string external;
declare variable $entity as xs:string external;
declare variable $system as xs:string external;
declare variable $originator as xs:string external;
declare variable $userID as xs:string external;
declare variable $operation as xs:string external;
declare variable $destination as xs:string external;
declare variable $timestamp as xs:string external;
declare variable $userValue as xs:string external;
declare variable $passwordValue as xs:string external;
declare variable $chanelValue as xs:string external;

xf:headerFSRequest($country,
    $lang,
    $entity,
    $system,
    $originator,
    $userID,
    $operation,
    $destination,
    $timestamp,
    $userValue,
    $passwordValue,
    $chanelValue)