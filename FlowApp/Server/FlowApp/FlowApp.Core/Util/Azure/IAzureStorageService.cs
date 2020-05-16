using System.Threading.Tasks;

namespace FlowApp.Core.Util.Azure
{
    public interface IAzureStorageService
    {
         Task<string> UploadFileToBlobAsync(string strFileName, string base64String, string fileMimeType);
    }
}
