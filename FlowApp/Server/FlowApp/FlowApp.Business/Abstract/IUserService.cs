using FlowApp.Entity.Models;
using System.Threading.Tasks;

namespace FlowApp.Business.Abstract
{
    public interface IUserService
    {
        User GetUser(int userId);
        User Save(User user);
        Task<User> SaveImageAsync(User user);
    }
}