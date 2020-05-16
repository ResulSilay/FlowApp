using Microsoft.Azure.Storage;
using Microsoft.Azure.Storage.Blob;
using System;
using System.IO;
using System.Threading.Tasks;

namespace FlowApp.Core.Util.Azure
{
    public class AzureStorageService: IAzureStorageService
    {
        string accessKey = string.Empty;

        public AzureStorageService()
        {
            this.accessKey = "DefaultEndpointsProtocol=https;AccountName=....;AccountKey=.....;EndpointSuffix=core.windows.net";
            //AppConfiguration.GetConfiguration("AccessStorageKey");
        }

        public string UploadFileToBlob(string strFileName, string base64String, string fileMimeType)
        {
            try
            {
                var _task = Task.Run(() => this.UploadFileToBlobAsync(strFileName, base64String, fileMimeType));
                _task.Wait();
                string fileUrl = _task.Result;
                return fileUrl;
            }
            catch (Exception ex)
            {
                throw (ex);
            }
        }

        public async void DeleteBlobData(string fileUrl)
        {
            Uri uriObj = new Uri(fileUrl);
            string BlobName = Path.GetFileName(uriObj.LocalPath);

            CloudStorageAccount cloudStorageAccount = CloudStorageAccount.Parse(accessKey);
            CloudBlobClient cloudBlobClient = cloudStorageAccount.CreateCloudBlobClient();
            string strContainerName = "uploads";
            CloudBlobContainer cloudBlobContainer = cloudBlobClient.GetContainerReference(strContainerName);

            string pathPrefix = DateTime.Now.ToUniversalTime().ToString("yyyy-MM-dd") + "/";
            CloudBlobDirectory blobDirectory = cloudBlobContainer.GetDirectoryReference(pathPrefix);
            CloudBlockBlob blockBlob = blobDirectory.GetBlockBlobReference(BlobName);
            await blockBlob.DeleteAsync();
        }


        private string GenerateFileName(string fileName)
        {
            string strFileName = string.Empty;
            string[] strName = fileName.Split('.');
            strFileName = DateTime.Now.ToUniversalTime().ToString("yyyy-MM-dd") + "/" + DateTime.Now.ToUniversalTime().ToString("yyyyMMdd\\THHmmssfff") + "." + strName[strName.Length - 1];
            return strFileName;
        }

        public async Task<string> UploadFileToBlobAsync(string strFileName, string base64String, string fileMimeType)
        {
            try
            {
                CloudStorageAccount cloudStorageAccount = CloudStorageAccount.Parse(accessKey);
                CloudBlobClient cloudBlobClient = cloudStorageAccount.CreateCloudBlobClient();
                string strContainerName = "uploads";
                CloudBlobContainer cloudBlobContainer = cloudBlobClient.GetContainerReference(strContainerName);
                string fileName = this.GenerateFileName(strFileName);

                if (await cloudBlobContainer.CreateIfNotExistsAsync())
                {
                    await cloudBlobContainer.SetPermissionsAsync(new BlobContainerPermissions { PublicAccess = BlobContainerPublicAccessType.Blob });
                }

                if (fileName != null && base64String != null)
                {
                    CloudBlockBlob cblob = cloudBlobContainer.GetBlockBlobReference(fileName);
                    cblob.Properties.ContentType = fileMimeType;

                    var bytes = Convert.FromBase64String(base64String);
                    using (var stream = new MemoryStream(bytes))
                    {
                        await cblob.UploadFromStreamAsync(stream);
                        return cblob.Uri.AbsoluteUri;
                    }

                    /*CloudBlockBlob cloudBlockBlob = cloudBlobContainer.GetBlockBlobReference(fileName);
                    cloudBlockBlob.Properties.ContentType = fileMimeType;
                    await cloudBlockBlob.UploadFromByteArrayAsync(fileData, 0, fileData.Length);
                    return cloudBlockBlob.Uri.AbsoluteUri;*/
                }
                return "";
            }
            catch (Exception ex)
            {
                throw (ex);
            }
        }
    }
}
