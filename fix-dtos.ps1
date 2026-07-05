# Fix all DTOs to remove Lombok and add manual getters/setters

$dtoFiles = @(
    "CodingAIRequestDTO.java",
    "CodingAIResponseDTO.java",
    "CareerGuidanceRequestDTO.java",
    "CareerGuidanceResponseDTO.java",
    "InterviewRequestDTO.java",
    "InterviewResponseDTO.java",
    "CompanyPreparationRequestDTO.java",
    "CompanyPreparationResponseDTO.java",
    "ChatRequestDTO.java",
    "ChatResponseDTO.java"
)

$dtoPath = "C:\Users\Selva\Desktop\projectjul03\careerpilot-backend\src\main\java\com\careerpilot\dto\ai"

foreach ($file in $dtoFiles) {
    $filePath = Join-Path $dtoPath $file
    if (Test-Path $filePath) {
        $content = Get-Content $filePath -Raw
        # Remove Lombok imports and annotations
        $content = $content -replace 'import lombok\..*?;', ''
        $content = $content -replace '@Data|@NoArgsConstructor|@AllArgsConstructor', ''
        $content = $content -replace '\n\s*\n', "`n"
        Set-Content $filePath $content
        Write-Host "Fixed: $file"
    }
}
