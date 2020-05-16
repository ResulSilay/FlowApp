using FlowApp.Core.Data;
using FlowApp.Entity.Models;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace FlowApp.Data.Abstract
{
    public interface IPostRepository : IEntityRepository<Post> 
    {
        List<ViewPost> GetPostsList();
        List<ViewPost> GetPostsList(int page,int pageSize);
        ViewPost GetPostList(int postId);
    }
}