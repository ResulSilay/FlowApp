using FlowApp.Core.DTOs;
using FlowApp.Entity.Models;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace FlowApp.Business.Abstract
{
    public interface IPostService
    {
        List<ViewPost> GetPostAll();
        List<ViewPost> GetPostPageAll(int page,int pageSize);
        ViewPost GetPost(int postId);
        ViewPost Save(int userId, PostDto postDto);
        bool Remove(int postId);
        Task<bool> Any(int postId);
    }
}