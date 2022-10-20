xquery version "2004-draft";
(:: pragma bea:global-element-return element="ns0:queryPurchasedOfferingRequest" location="../schemas/XMLSchema_290342992.xsd" ::)

declare namespace ns0 = "http://telefonica.com/globalIntegration/services/QueryPurchasedOffering/v1";
declare namespace xf = "http://tempuri.org/APP_Movil/Resources/Common/xquery/QueryPurchasedOfferingRequest/";

declare function xf:QueryPurchasedOfferingRequest($phone as xs:string)
    as element(ns0:queryPurchasedOfferingRequest) {
        <ns0:queryPurchasedOfferingRequest>
            <ns0:reqBodyQPOItem>
                <ns0:requestChoiceQPOItem>
                    <ns0:primaryTelephoneNumber>{ $phone }</ns0:primaryTelephoneNumber>
                </ns0:requestChoiceQPOItem>
            </ns0:reqBodyQPOItem>
        </ns0:queryPurchasedOfferingRequest>
};

declare variable $phone as xs:string external;

xf:QueryPurchasedOfferingRequest($phone)