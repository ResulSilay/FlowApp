using FlowApp.Core.DTOs;
using FlowApp.Entity.Models;
using System.Threading.Tasks;

namespace FlowApp.Business.Abstract
{
    public interface IRateService
    {
        Rating GetRate(int postId);
        Rating GetRate(int postId,int userId);
        Rating Save(Rating rating);
        Rating Update(Rating rating);
        Task<bool> Any(int postId);
        Task<bool> Any(int postId,int userId);
    }
}