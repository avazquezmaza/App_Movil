<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:head="http://telefonica.com/globalIntegration/header" xmlns:v1="http://telefonica.com/globalIntegration/services/QueryPurchasedOffering/v1">
   <soapenv:Header>
        <ns0:HeaderIn>
         <ns0:country>co</ns0:country>
         <ns0:lang>es</ns0:lang>
         <ns0:entity>TEF</ns0:entity>
         <ns0:system>USSD</ns0:system>
         <ns0:subsystem>USSD</ns0:subsystem>
         <ns0:originator>co:es:TEF: APP:APP</ns0:originator>
         <ns0:userId>22</ns0:userId>
         <ns0:operation>QueryPurchasedOffering_v1</ns0:operation>
         <ns0:destination>co:es:TEF: APP:APP</ns0:destination>
         <ns0:execId>{fn-bea:uuid()}</ns0:execId>
         <ns0:timestamp>{fn:current-dateTime()}</ns0:timestamp>
        <ns0:varArg>
         <!--1 to 10 repetitions:-->
            <ns0:arg>
               <ns0:key>User</ns0:key>
               <ns0:values>
                  <ns0:value>Greta_USSD</ns0:value>
               </ns0:values>
            </ns0:arg>
            <ns0:arg>
               <ns0:key>Password</ns0:key>
               <ns0:values>
                  <ns0:value>qXQvjReSEjy22iaNjPAPB8btX4oEgsgt6nNmTzkQmpU=</ns0:value>
               </ns0:values>
            </ns0:arg>
            <ns0:arg>
               <ns0:key>Channel</ns0:key>
               <ns0:values>
                 <ns0:value>22</ns0:value>
               </ns0:values>
            </ns0:arg>
         </ns0:varArg>
        </ns0:HeaderIn>
   </soapenv:Header>
   <soapenv:Body>
      <v1:queryPurchasedOfferingRequest>
         <v1:reqBodyQPOItem>
            <v1:requestChoiceQPOItem>
               <v1:primaryTelephoneNumber>${headers.primaryTelephoneNumber}</v1:primaryTelephoneNumber>
            </v1:requestChoiceQPOItem>
         </v1:reqBodyQPOItem>
      </v1:queryPurchasedOfferingRequest>
   </soapenv:Body>
</soapenv:Envelope>