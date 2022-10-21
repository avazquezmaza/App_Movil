<#assign aDateTime = .now>
<![CDATA[<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:head="http://telefonica.com/globalIntegration/header" xmlns:v1="http://telefonica.com/globalIntegration/services/CreateInteraction/v1">
	<soapenv:Header>
        <ns0:HeaderIn>
         <ns0:country>co</ns0:country>
         <ns0:lang>Es</ns0:lang>
         <ns0:entity>TEF</ns0:entity>
         <ns0:system>APP</ns0:system>
         <ns0:subsystem>APP</ns0:subsystem>
         <ns0:originator>co:es:TEF: APP:APP</ns0:originator>
         <ns0:userId>13</ns0:userId>
         <ns0:operation>BalanceTransfer</ns0:operation>
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
                 <ns0:value>13</ns0:value>
               </ns0:values>
            </ns0:arg>
         </ns0:varArg>
        </ns0:HeaderIn>
	</soapenv:Header>
	<soapenv:Body>
		<ns0:createInteractionRequest>
			<ns0:taskRequestIItem>
				<ns0:ModalityDesc>0</ns0:ModalityDesc>
				<ns0:primaryTelephoneNumber>${headers.primaryTelephoneNumber}</ns0:primaryTelephoneNumber>
				<ns0:IDGeographicAddress>0</ns0:IDGeographicAddress>
				<ns0:contactTypePartyAccountContact>${headers.contactTypePartyAccountContact}</ns0:contactTypePartyAccountContact>
				<ns0:descriptionBusinessInteraction>${headers.descriptionBusinessInteraction}</ns0:descriptionBusinessInteraction>
			</ns0:taskRequestIItem>
		</ns0:createInteractionRequest>
	</soapenv:Body>
</soapenv:Envelope>]]>