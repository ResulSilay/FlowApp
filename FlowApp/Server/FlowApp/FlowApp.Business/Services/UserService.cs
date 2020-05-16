using System.Threading.Tasks;
using FlowApp.Business.Abstract;
using FlowApp.Core.Util.Azure;
using FlowApp.Core.Util.Hash;
using FlowApp.Data.Abstract;
using FlowApp.Entity.Models;

namespace FlowApp.Business.Services
{
    public class UserService : IUserService
    {
        public readonly IUserRepository _userRepository;
        private readonly IAzureStorageService _azureStorageService;

        public UserService(IUserRepository userRepository, IAzureStorageService azureStorageService)
        {
            _userRepository = userRepository;
            _azureStorageService = azureStorageService;
        }

        public User GetUser(int userId)
        {
            return _userRepository.Get(x => x.Id == userId);
        }

        public async Task<bool> AnyAsync(string email)
        {
            return await _userRepository.Any(x => x.Email == email);
        }

        public User Save(User user)
        {
           return _userRepository.Update(user);
        }

        public async Task<User> SaveImageAsync(User user)
        {
            if (user.Img != null)
                user.Img = await _azureStorageService.UploadFileToBlobAsync("img", user.Img, "image/jpeg");
            return _userRepository.Update(user);
        }
    }
}
