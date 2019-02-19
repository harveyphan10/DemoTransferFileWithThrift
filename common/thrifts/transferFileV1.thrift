namespace java thrift.services

// content for 1 time upload much < 7MB
struct FileInfo {
	1: string fileName,
	2: i64 length,
	3: binary content
}

service FileUpload {
	i64 uploadFile(1: FileInfo fileInfo)
}
