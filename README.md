# DemoTransferFileWithThrift

Project demo gồm 4 phần:
  + Project server: TransferFileV1
  + Project client: TransferFileClient
  + Folder common chứa file .thrift.
  + Folder 3rdlib chứa thư viện cần cho project.
  
- Server cung cấp hàm upload 1 file, sử dụng nonblocking server. 
- Với tính chất demo thì trong project client đã có sẵn thư mục data, dữ liệu sẽ được gửi đi từ thư mục này.
- Folder storage tại prsẽ là nơi chứa những files mà client truyền lên.
