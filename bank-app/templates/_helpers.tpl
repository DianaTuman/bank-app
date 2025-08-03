{{- define "umbrella-chart.commonServiceAccountName" -}}
common-service-account
{{- end }}

{{- define "bank.accountsServiceURL" -}}
http://{{ .Release.Name }}-accounts-service:8081
{{- end }}

{{- define "bank.blockerServiceURL" -}}
http://{{ .Release.Name }}-blocker-service:8082
{{- end }}

{{- define "bank.cashServiceURL" -}}
http://{{ .Release.Name }}-cash-service:8083
{{- end }}

{{- define "bank.exchangeServiceURL" -}}
http://{{ .Release.Name }}-exchange-service:8084
{{- end }}

{{- define "bank.notificationsServiceURL" -}}
http://{{ .Release.Name }}-notifications-service:8086
{{- end }}

{{- define "bank.transferServiceURL" -}}
http://{{ .Release.Name }}-transfer-service:8087
{{- end }}

{{- define "bank.bankUiURL" -}}
http://{{ .Release.Name }}-bank-ui:8080
{{- end }}

{{- define "bank.gatewayApiURL" -}}
http://{{ .Release.Name }}-gateway-api:8090
{{- end }}