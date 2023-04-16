<#if (locale.currentLanguageTag!"en") == "en">
  <#include "terms-en.ftl" />
<#else>
  <#include "terms-ru.ftl" />
</#if>
<#import "template.ftl" as t>